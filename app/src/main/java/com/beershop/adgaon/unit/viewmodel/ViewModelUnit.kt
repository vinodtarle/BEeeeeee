package com.beershop.adgaon.unit.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.beershop.adgaon.base.viewmodel.BaseViewModel
import com.beershop.adgaon.unit.repository.RepositoryUnit
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewModelUnit @ViewModelInject internal constructor(
    private val repository: RepositoryUnit
) : BaseViewModel(repository) {

    fun getUnit() {
        viewModelScope.launch {
            repository.orderBy().collect {
                collectionListener.value = it
            }
        }
    }
}
