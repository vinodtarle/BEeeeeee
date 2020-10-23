package com.beershop.adgaon.supplier.view

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
import com.beershop.adgaon.supplier.adapter.AdapterSupplier
import com.beershop.adgaon.supplier.model.Supplier
import com.beershop.adgaon.supplier.viewmodel.ViewModelSupplier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_supplier.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentSupplier : BaseFragment(layoutResourceId = R.layout.fragment_supplier) {

    override fun className() = this.javaClass.simpleName

    @Inject
    lateinit var adapter: AdapterSupplier

    private val viewModelSupplier: ViewModelSupplier by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(R.string.title_supplier)
    }

    override fun initData() {
        viewModelSupplier.collectionListener()
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
    }

    private fun updateCollection(collection: List<Supplier>) {
        adapter.setCollection(collection = collection)
    }

    override fun initNavigation() {
        fab.setOnClickListener {
            navigateTo(R.id.fragmentSupplierAdd)
        }
    }

    override fun initRecyclerView() {
        recyclerView.init(requireContext())
        recyclerView.adapter = adapter
    }
}