package com.beershop.adgaon.supplier.view

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
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.databinding.FragmentSupplierAddBinding
import com.beershop.adgaon.supplier.model.Supplier
import com.beershop.adgaon.supplier.viewmodel.ViewModelSupplier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_supplier_add.*
import java.util.*

@AndroidEntryPoint
class FragmentSupplierAdd : BaseFragment(layoutResourceId = R.layout.fragment_supplier_add) {

    override fun className() = this.javaClass.simpleName

    private val viewModelSupplier: ViewModelSupplier by viewModels()

    private lateinit var binding: FragmentSupplierAddBinding
    private lateinit var supplier: Supplier
    private lateinit var ui: UI

    private var isUpdate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSupplierAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.title_add_supplier)
    }

    override fun init() {
        ui = UI()
        supplier = Supplier()
        binding.ui = ui
        binding.supplier = supplier
    }

    override fun initObserver() {
        viewModelSupplier.add.observe(viewLifecycleOwner, { result ->
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

    override fun initListener() {
        edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                supplier.status = !s.isNullOrBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        buttonAdd.setOnClickListener {
            hideKeyboard()
            if (validate()) {
                if (!isUpdate) {
                    supplier.apply {
                        createdAt = Date()
                        createdBy = "Admin"
                    }
                    addDocument()
                } //else updateUnit()
            } //else showErrorInput("Enter item name!!")
        }
    }

    private fun validate(): Boolean {
        return supplier.name.isNotBlank() && supplier.mobileNumber.isNotBlank()
    }

    private fun addDocument() {
        viewModelSupplier.add(data = supplier)
    }

    private fun clear() {
        isUpdate = false
        ui.update = false
        supplier = Supplier()
        binding.supplier = supplier
    }
}