package com.beershop.adgaon.stock.view

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
import com.beershop.adgaon.stock.adapter.AdapterStock
import com.beershop.adgaon.stock.model.Stock
import com.beershop.adgaon.stock.viewmodel.ViewModelStock
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_stock.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentStock : BaseFragment(layoutResourceId = R.layout.fragment_stock) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterStock


    @Inject
    lateinit var firebase: FirebaseFirestore

    private val viewModelStock: ViewModelStock by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.title_stock)
    }

    override fun initData() {
        viewModelStock.collectionListener()
    }

    override fun initObservers() {
        viewModelStock.collectionListener.observe(viewLifecycleOwner, { result ->
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

    private fun updateCollection(collection: List<Stock>) {
        adapter.setCollection(collection = collection)
    }

    override fun initNavigation() {
        fab.setOnClickListener {
            navigateTo(R.id.fragmentStockAdd)
        }
    }

    override fun initRecyclerView() {
        recyclerView.init(requireContext())
        recyclerView.adapter = adapter
    }
}