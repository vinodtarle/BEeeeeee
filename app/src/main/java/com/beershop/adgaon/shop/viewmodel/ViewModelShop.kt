package com.beershop.adgaon.shop.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.beershop.adgaon.base.viewmodel.BaseViewModel
import com.beershop.adgaon.shop.repository.RepositoryShop

class ViewModelShop @ViewModelInject internal constructor(
    private val repository: RepositoryShop
) : BaseViewModel(repository) {

}
