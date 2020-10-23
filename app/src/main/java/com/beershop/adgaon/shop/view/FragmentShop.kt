package com.beershop.adgaon.shop.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.beershop.adgaon.R
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.utility.constant.toObjects
import com.beershop.adgaon.base.utility.extension.init
import com.beershop.adgaon.base.utility.extension.navigateTo
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.shop.adapter.AdapterShop
import com.beershop.adgaon.shop.model.Shop
import com.beershop.adgaon.shop.viewmodel.ViewModelShop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_item.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentShop : BaseFragment(layoutResourceId = R.layout.fragment_shop) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterShop

    private val viewModelShop: ViewModelShop by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()
    }

    override fun initData() {
        viewModelShop.collectionListener()
    }

    override fun initObservers() {
        viewModelShop.collectionListener.observe(viewLifecycleOwner, { result ->
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

    private fun updateCollection(collection: List<Shop>) {
        adapter.setCollection(collection = collection)
    }

    override fun initNavigation() {
        fab.setOnClickListener {
            navigateTo(R.id.fragmentShopAdd)
        }
    }

    override fun initRecyclerView() {
        recyclerView.init(requireContext())
        recyclerView.adapter = adapter
    }
}