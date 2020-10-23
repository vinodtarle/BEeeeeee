package com.beershop.adgaon.sales.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beershop.adgaon.R
import com.beershop.adgaon.base.model.UI
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.utility.constant.Constant.Companion.getCurrentDate
import com.beershop.adgaon.base.utility.constant.Constant.Companion.getUniqueNumber
import com.beershop.adgaon.base.utility.constant.toObject
import com.beershop.adgaon.base.utility.extension.init
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.databinding.FragmentOrderAddBinding
import com.beershop.adgaon.item.model.Item
import com.beershop.adgaon.item.viewmodel.ViewModelItem
import com.beershop.adgaon.sales.adapter.AdapterSaleItem
import com.beershop.adgaon.sales.model.Sale
import com.beershop.adgaon.sales.viewmodel.ViewModelSale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order_add.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@AndroidEntryPoint
class FragmentSaleAdd : BaseFragment(layoutResourceId = R.layout.fragment_order_add) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterSaleItem

    private val viewModelSale: ViewModelSale by viewModels()
    private val viewModelItem: ViewModelItem by viewModels()

    private lateinit var binding: FragmentOrderAddBinding
    private lateinit var sale: Sale
    private lateinit var ui: UI

    private var isUpdate = false
    private var items = HashMap<String, Item>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentOrderAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()
    }

    override fun init() {
        ui = UI()
        sale = Sale()
        binding.ui = ui
        binding.sale = sale
    }

    override fun initObserver() {
        viewModelSale.add.observe(viewLifecycleOwner, { result ->
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

    override fun initListener() {
        buttonAddNew.setOnClickListener {
            val itemNumber = edNumber.text.toString()
            viewModelItem.document(itemNumber)
        }

        buttonAdd.setOnClickListener {
            addDocument()

        }
    }

    private fun updateDocument(document: Item?) {
        document?.let {
            if (items.containsKey(it.number)) {
                items[it.number]?.apply {
                    var qty = quantity.toInt()
                    qty += 1
                    quantity = qty.toString()
                }
            } else {
                it.quantity = "1"
                items[it.number] = it
            }
            updateCollection(items)
        }
    }

    private fun updateCollection(collection: HashMap<String, Item>) {
        val items = collection.values
        adapter.setCollection(collection = items.toList())
    }

    override fun initRecyclerView() {
        recyclerView.init(requireContext())
        recyclerView.adapter = adapter
    }

    private fun validate(): Boolean {
        return true
    }

    private fun addDocument() {
        viewModelSale.add(
            data = sale.also {
                it.number = getUniqueNumber()
                it.date = getCurrentDate()
                it.createdAt = Date()
                it.createdBy = "Admin"
                it.items = items
            })
    }

    private fun clear() {
        isUpdate = false
        ui.update = false
        sale = Sale()
        items.clear()
        binding.sale = sale
        adapter.setCollection(emptyList())
    }
}