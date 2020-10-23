package com.beershop.adgaon.sales.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.beershop.adgaon.base.viewmodel.BaseViewModel
import com.beershop.adgaon.sales.repository.RepositorySale

class ViewModelSale @ViewModelInject internal constructor(
    private val repository: RepositorySale
) : BaseViewModel(repository) {

}
