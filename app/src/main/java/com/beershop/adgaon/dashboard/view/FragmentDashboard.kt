package com.beershop.adgaon.dashboard.view

import android.os.Bundle
import android.view.View
import com.beershop.adgaon.R
import com.beershop.adgaon.base.utility.extension.homeBackButton
import com.beershop.adgaon.base.utility.extension.navigateTo
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*

@AndroidEntryPoint
class FragmentDashboard : BaseFragment(layoutResourceId = R.layout.fragment_dashboard) {

    override fun className() = this.javaClass.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()

        setTitle(R.string.title_home)
        homeBackButton(false)
    }

    override fun initListener() {
        cardSale.setOnClickListener {
            navigateTo(R.id.fragmentSale)
        }
        cardSaleManual.setOnClickListener {
            navigateTo(R.id.fragmentBarcode)
        }
        cardStock.setOnClickListener {
            navigateTo(R.id.fragmentStock)
        }
        cardItem.setOnClickListener {
            navigateTo(R.id.fragmentItem)
        }
        cardSupplier.setOnClickListener {
            navigateTo(R.id.fragmentSupplier)
        }
    }
}