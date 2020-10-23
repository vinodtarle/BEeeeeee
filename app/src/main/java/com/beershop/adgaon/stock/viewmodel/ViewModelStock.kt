package com.beershop.adgaon.stock.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.beershop.adgaon.base.viewmodel.BaseViewModel
import com.beershop.adgaon.stock.repository.RepositoryStock

class ViewModelStock @ViewModelInject internal constructor(
    private val repository: RepositoryStock
) : BaseViewModel(repository) {

}
