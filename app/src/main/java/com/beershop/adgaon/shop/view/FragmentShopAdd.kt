package com.beershop.adgaon.shop.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.beershop.adgaon.R
import com.beershop.adgaon.base.model.UI
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.utility.extension.setTitle
import com.beershop.adgaon.base.view.BaseFragment
import com.beershop.adgaon.databinding.FragmentShopAddBinding
import com.beershop.adgaon.shop.model.Shop
import com.beershop.adgaon.shop.viewmodel.ViewModelShop
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_supplier_add.*
import java.util.*

@AndroidEntryPoint
class FragmentShopAdd : BaseFragment(layoutResourceId = R.layout.fragment_shop_add) {

    override fun className() = this.javaClass.simpleName

    private val viewModelShop: ViewModelShop by viewModels()

    private lateinit var binding: FragmentShopAddBinding
    private lateinit var shop: Shop
    private lateinit var ui: UI

    private var isUpdate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentShopAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()
    }

    override fun init() {
        ui = UI()
        shop = Shop()
        binding.ui = ui
        binding.shop = shop
    }

    override fun initObserver() {
        viewModelShop.add.observe(viewLifecycleOwner, { result ->
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

    override fun initListener() {
        edName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shop.status = !s.isNullOrBlank()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        buttonAdd.setOnClickListener {
            hideKeyboard()
            if (validate()) {
                if (!isUpdate) {
                    shop.apply {
                        createdAt = Date()
                        createdBy = "Admin"
                    }
                    addDocument()
                } //else updateUnit()
            } //else showErrorInput("Enter item name!!")
        }
    }

    private fun validate(): Boolean {
        return shop.name.isNotBlank() && shop.mobileNumber.isNotBlank()
    }

    private fun addDocument() {
        viewModelShop.add(data = shop)
    }

    private fun clear() {
        isUpdate = false
        ui.update = false
        shop = Shop()
        binding.shop = shop
    }
}