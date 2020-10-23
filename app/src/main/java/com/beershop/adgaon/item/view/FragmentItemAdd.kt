package com.beershop.adgaon.item.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.beershop.adgaon.R
import com.beershop.adgaon.barcode.viewmodel.ViewModelBarcode
import com.beershop.adgaon.base.model.UI
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.utility.constant.toObject
import com.beershop.adgaon.base.utility.constant.toObjects
import com.beershop.adgaon.base.utility.extension.navigateTo
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.databinding.FragmentItemAddBinding
import com.beershop.adgaon.item.adapter.AdapterItem
import com.beershop.adgaon.item.model.Item
import com.beershop.adgaon.item.viewmodel.ViewModelItem
import com.beershop.adgaon.unit.model.Unit
import com.beershop.adgaon.unit.viewmodel.ViewModelUnit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_item_add.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentItemAdd : BaseFragment(layoutResourceId = R.layout.fragment_item_add) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterItem

    private val viewModelItem: ViewModelItem by viewModels()
    private val viewModelUnit: ViewModelUnit by viewModels()
    private val viewModelBarcode: ViewModelBarcode by activityViewModels()

    private lateinit var binding: FragmentItemAddBinding
    private lateinit var item: Item
    private lateinit var ui: UI

    private var isUpdate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentItemAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.title_add_item)
    }

    override fun init() {
        ui = UI()
        item = Item()
        binding.ui = ui
        binding.item = item
    }

    override fun initData() {
        viewModelUnit.getUnit()
        setType()
    }

    override fun initObserver() {
        viewModelItem.set.observe(viewLifecycleOwner, { result ->
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

        viewModelBarcode.barcode.observe(viewLifecycleOwner, { barcode ->
            if (barcode.isNotEmpty()) {
                document(documentId = barcode)
            }
        })

        viewModelItem.document.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Success -> {
                    updateDocument(
                        document = result.type?.document?.toObject()
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

    private fun document(documentId: String) {
        item.number = documentId
        viewModelItem.document(documentId)
    }

    private fun updateDocument(document: Item?) {
        document?.let {
            buttonAdd.isEnabled = false
            item = it
            showMessage(R.string.msg_item_exits)
            binding.item = item
        }
    }

    private fun updateCollection(collection: List<Unit>) {
        binding.spUnit.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.view_drop_down,
                collection
            )
        )
    }

    private fun setType() {
        binding.spType.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.view_drop_down,
                arrayListOf("Bottle", "Tin", "Box", "Pack", "Unit")
            )
        )
    }

    override fun initListener() {
        layoutNumber.setEndIconOnClickListener {
            navigateTo(R.id.fragmentBarcode)
        }

        spUnit.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                item.unit = parent.getItemAtPosition(position) as Unit
            }

        spType.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                item.type = parent.getItemAtPosition(position) as String
            }

        edNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                item.status = !s.isNullOrBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        buttonAdd.setOnClickListener {
            hideKeyboard()
            if (validate()) {
                if (!isUpdate) {
                    item.apply {
                        id = number
                        createdAt = Date()
                        createdBy = "Admin"
                    }
                    addDocument()
                } //else updateUnit()
            } //else showErrorInput("Enter item name!!")
        }
    }

    private fun validate(): Boolean {
        return item.number.isNotBlank()
                && item.number.length == 13
                && item.name.isNotBlank()
                && item.unit != null
                && item.type.isNotBlank()
                && item.quantity.isNotBlank()
    }

    private fun addDocument() {
        viewModelItem.set(documentId = item.number, data = item)
    }

    private fun clear() {
        isUpdate = false
        ui.update = false
        item = Item()
        binding.item = item
        clearBarcode()
    }

    private fun clearBarcode() {
        viewModelBarcode.barcode()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_item, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAddUnit -> {
                navigateTo(R.id.fragmentUnitAdd)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearBarcode()
    }
}