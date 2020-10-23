package com.beershop.adgaon.sales.view

import android.os.Bundle
import android.view.View
import com.beershop.adgaon.R
import com.beershop.adgaon.base.utility.extension.init
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.sales.adapter.AdapterSaleItem
import com.beershop.adgaon.sales.model.Sale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order_details.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSaleDetails : BaseFragment(layoutResourceId = R.layout.fragment_order_details) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterSaleItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val sale = it.get("document") as Sale
            println(">>>>> ??? ${sale.number}")
            adapter.setCollection(collection = sale.items?.values?.toList() ?: emptyList())
        }

        setTitle()
    }

    override fun initRecyclerView() {
        recyclerView.init(requireContext())
        recyclerView.adapter = adapter
    }
}