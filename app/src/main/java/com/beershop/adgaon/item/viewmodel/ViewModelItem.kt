package com.beershop.adgaon.item.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.beershop.adgaon.base.viewmodel.BaseViewModel
import com.beershop.adgaon.item.repository.RepositoryItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ViewModelItem @ViewModelInject internal constructor(
    private val repository: RepositoryItem
) : BaseViewModel(repository) {

    fun getItem() {
        viewModelScope.launch {
            repository.getItem().collect {
                collectionListener.value = it
            }
        }
    }
}
