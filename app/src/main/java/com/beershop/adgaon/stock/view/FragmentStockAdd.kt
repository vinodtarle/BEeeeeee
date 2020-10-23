package com.beershop.adgaon.stock.view

import android.os.Bundle
import android.view.LayoutInflater
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
import com.beershop.adgaon.base.utility.constant.Constant.Companion.getCurrentDate
import com.beershop.adgaon.base.utility.constant.toObject
import com.beershop.adgaon.base.utility.constant.toObjects
import com.beershop.adgaon.base.utility.extension.navigateTo
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.databinding.FragmentStockAddBinding
import com.beershop.adgaon.item.model.Item
import com.beershop.adgaon.item.viewmodel.ViewModelItem
import com.beershop.adgaon.stock.model.Stock
import com.beershop.adgaon.stock.viewmodel.ViewModelStock
import com.beershop.adgaon.supplier.model.Supplier
import com.beershop.adgaon.supplier.viewmodel.ViewModelSupplier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stock_add.*
import java.util.*

@AndroidEntryPoint
class FragmentStockAdd : BaseFragment(layoutResourceId = R.layout.fragment_stock_add) {

    override fun className() = this.javaClass.simpleName

    private val viewModelStock: ViewModelStock by viewModels()
    private val viewModelSupplier: ViewModelSupplier by viewModels()
    private val viewModelItem: ViewModelItem by viewModels()
    private val viewModelBarcode: ViewModelBarcode by activityViewModels()

    private lateinit var binding: FragmentStockAddBinding
    private lateinit var stock: Stock
    private lateinit var supplier: Supplier
    private lateinit var item: Item
    private lateinit var ui: UI

    private var isUpdate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentStockAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.title_add_stock)
    }

    override fun init() {
        ui = UI()
        stock = Stock()
        supplier = Supplier()
        item = Item()

        ui.date = getCurrentDate()

        stock.supplier = supplier
        stock.item = item

        binding.ui = ui
        binding.stock = stock
    }

    override fun initData() {
        viewModelSupplier.collectionListener()
    }

    override fun initObserver() {
        viewModelStock.add.observe(viewLifecycleOwner, { result ->
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
        viewModelSupplier.collectionListener.observe(viewLifecycleOwner, { result ->
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
                    showMessage(R.string.msg_item_not_found)
                    buttonAdd.isEnabled = false
                }
            }
        })

        viewModelBarcode.barcode.observe(viewLifecycleOwner, { barcode ->
            if (barcode.isNotEmpty()) {
                document(documentId = barcode)
            }
        })
    }

    private fun updateDocument(document: Item?) {
        document?.let {
            item = it
            stock.item = item
            binding.stock = stock
        }
    }

    private fun updateCollection(collection: List<Supplier>) {
        binding.spSupplier.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.view_drop_down,
                collection
            )
        )
    }

    override fun initListener() {
        edDate.setOnClickListener {
            hideKeyboard()
            datePickerDialog { date ->
                ui.date = date
            }
        }

        spSupplier.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                stock.supplier = parent.getItemAtPosition(position) as Supplier
            }

        layoutNumber.setEndIconOnClickListener {
            navigateTo(R.id.fragmentBarcode)
        }

        buttonAdd.setOnClickListener {
            hideKeyboard()
            if (validate()) {
                if (!isUpdate) {
                    stock.apply {
                        createdAt = Date()
                        createdBy = "Admin"
                    }
                    addDocument()
                } //else updateUnit()
            } //else showErrorInput("Enter item name!!")
        }
    }

    private fun document(documentId: String) {
        item.number = documentId
        viewModelItem.document(documentId)
    }

    private fun validate(): Boolean {
        return true
//        return stock.date.isNotBlank()
//                && stock.supplier != null
//                && stock.item != null
//                && stock.mrp.isNotBlank()
//                && stock.quantity.isNotBlank()
    }

    private fun addDocument() {
        viewModelStock.add(data = stock)
    }

    private fun clear() {
        isUpdate = false
        ui.update = false
        supplier = Supplier()
        item = Item()
        clearBarcode()
    }

    private fun clearBarcode() {
        viewModelBarcode.barcode()
    }

    override fun onDestroy() {
        super.onDestroy()
        clearBarcode()
    }
}