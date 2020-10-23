package com.beershop.adgaon.item.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.beershop.adgaon.R
import com.beershop.adgaon.barcode.viewmodel.ViewModelBarcode
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.utility.constant.toObjects
import com.beershop.adgaon.base.utility.extension.init
import com.beershop.adgaon.base.utility.extension.navigateTo
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.item.adapter.AdapterItem
import com.beershop.adgaon.item.model.Item
import com.beershop.adgaon.item.viewmodel.ViewModelItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_item.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentItem : BaseFragment(layoutResourceId = R.layout.fragment_item) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterItem

    private val viewModelItem: ViewModelItem by viewModels()
    private val viewModelBarcode: ViewModelBarcode by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.title_item)
    }

    override fun initData() {
        viewModelItem.getItem()
    }

    override fun initObservers() {
        viewModelItem.collectionListener.observe(viewLifecycleOwner, { result ->
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

    private fun updateCollection(collection: List<Item>) {
        adapter.setCollection(collection = collection)
    }

    override fun initNavigation() {
        fab.setOnClickListener {
            navigateTo(R.id.fragmentItemAdd)
        }
    }

    override fun initRecyclerView() {
        recyclerView.init(requireContext())
        recyclerView.adapter = adapter
    }
}