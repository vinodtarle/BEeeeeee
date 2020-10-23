package com.beershop.adgaon.unit.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beershop.adgaon.R
import com.beershop.adgaon.base.model.UI
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.utility.constant.Constant.Companion.BLANK
import com.beershop.adgaon.base.utility.constant.toObjects
import com.beershop.adgaon.base.utility.extension.init
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.databinding.FragmentUnitAddBinding
import com.beershop.adgaon.unit.adapter.AdapterUnit
import com.beershop.adgaon.unit.model.Unit
import com.beershop.adgaon.unit.viewmodel.ViewModelUnit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_unit_add.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentUnitAdd : BaseFragment(layoutResourceId = R.layout.fragment_unit_add) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterUnit
    private val viewModelUnit: ViewModelUnit by viewModels()

    private lateinit var binding: FragmentUnitAddBinding
    private lateinit var unit: Unit
    private lateinit var ui: UI

    private var isUpdate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentUnitAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()
    }

    override fun init() {
        ui = UI()
        unit = Unit()
        binding.ui = ui
        binding.unit = unit
    }

    override fun initData() {
        viewModelUnit.getUnit()
    }

    override fun initObserver() {
        viewModelUnit.add.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    clear()
                    hideProgressBar()
                    showSuccessAdd()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    showErrorAdd()
                }
            }
        })
    }

    override fun initObservers() {
        viewModelUnit.collectionListener.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    updateCollection(
                        collection = result.type?.collection?.toObjects() ?: emptyList()
                    )
                    hideProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    showFullScreenError()
                }
            }
        })
    }

    private fun updateCollection(collection: List<Unit>) {
        adapter.setCollection(collection = collection)
    }

    override fun initRecyclerView() {
        recyclerView.init(requireContext())
        recyclerView.adapter = adapter
    }

    override fun initListener() {
        edUnit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                unit.status = !s.isNullOrBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        ivCancel.setOnClickListener { clear() }

        buttonAdd.setOnClickListener {
            hideKeyboard()
            if (validate()) {
                if (!isUpdate) {
                    unit.id = BLANK
                    unit.createdAt = Date()
                    unit.createdBy = "Admin"
                    addDocument()
                } //else updateUnit()
            } //else showErrorInput("Enter unit name!!")
        }
    }

    private fun validate(): Boolean {
        return unit.name.isNotBlank()
    }

    private fun addDocument() {
        viewModelUnit.add(data = unit)
    }

    private fun clear() {
        isUpdate = false
        ui.update = false
        unit = Unit()
        binding.unit = unit
    }
}