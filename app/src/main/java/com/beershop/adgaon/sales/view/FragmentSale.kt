package com.beershop.adgaon.sales.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.beershop.adgaon.R
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.utility.constant.toObjects
import com.beershop.adgaon.base.utility.extension.init
import com.beershop.adgaon.base.utility.extension.navigateTo
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.bottomsheet.view.BottomSheet
import com.beershop.adgaon.sales.adapter.AdapterSale
import com.beershop.adgaon.sales.model.Sale
import com.beershop.adgaon.sales.viewmodel.ViewModelSale
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_order.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSale : BaseFragment(layoutResourceId = R.layout.fragment_order) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterSale

    private val viewModelSale: ViewModelSale by viewModels()

    private lateinit var bottomSheet: BottomSheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()
        initBottomSheet()
    }

    override fun initData() {
        viewModelSale.collectionListener()
    }

    private fun initBottomSheet() {
        bottomSheet = BottomSheet()
        bottomSheet.show(requireActivity().supportFragmentManager, BottomSheet.TAG)

    }

    override fun initObservers() {
        viewModelSale.collectionListener.observe(viewLifecycleOwner, { result ->
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

    private fun updateCollection(collection: List<Sale>) {
        adapter.setCollection(collection = collection)
        adapter.itemClick = {
            navigateTo(
                R.id.fragmentSaleDetails,
                bundleOf(
                    "document" to it
                )
            )
        }
    }

    override fun initNavigation() {
        fab.setOnClickListener {
            navigateTo(R.id.fragmentSaleAdd)
        }
    }

    override fun initRecyclerView() {
        recyclerView.init(requireContext())
        recyclerView.adapter = adapter
    }
}