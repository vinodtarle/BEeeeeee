package com.beershop.adgaon.supplier.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.beershop.adgaon.base.viewmodel.BaseViewModel
import com.beershop.adgaon.supplier.repository.RepositorySupplier

class ViewModelSupplier @ViewModelInject internal constructor(
    private val repository: RepositorySupplier
) : BaseViewModel(repository) {

}
