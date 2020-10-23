package com.beershop.adgaon.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beershop.adgaon.base.repository.BaseRepository
import com.beershop.adgaon.base.repository.Resource
import com.beershop.adgaon.base.repository.Response
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel() {

    val add: MutableLiveData<Resource<Response>> = MutableLiveData()
    val set: MutableLiveData<Resource<Response>> = MutableLiveData()
    val update: MutableLiveData<Resource<Response>> = MutableLiveData()
    val delete: MutableLiveData<Resource<Response>> = MutableLiveData()
    val document: MutableLiveData<Resource<Response>> = MutableLiveData()
    val documentListener: MutableLiveData<Resource<Response>> = MutableLiveData()
    val collection: MutableLiveData<Resource<Response>> = MutableLiveData()
    val collectionListener: MutableLiveData<Resource<Response>> = MutableLiveData()

    fun add(data: Any) {
        viewModelScope.launch {
            repository.add(data).collect {
                add.value = it
            }
        }
    }

    fun set(documentId: String, data: Any) {
        viewModelScope.launch {
            repository.set(documentId = documentId, data = data).collect {
                set.value = it
            }
        }
    }

    fun update(documentId: String, key: String, value: Any) {
        viewModelScope.launch {
            repository.update(
                documentId = documentId,
                key = key,
                value = value
            ).collect {
                update.value = it
            }
        }
    }

    fun delete(documentId: String) {
        viewModelScope.launch {
            repository.delete(documentId).collect {
                delete.value = it
            }
        }
    }

    fun document(documentId: String) {
        viewModelScope.launch {
            repository.document(documentId).collect {
                document.value = it
            }
        }
    }

    fun documentListener(documentId: String) {
        viewModelScope.launch {
            repository.documentListener(documentId).collect {
                documentListener.value = it
            }
        }
    }

    fun collection() {
        viewModelScope.launch {
            repository.collection().collect {
                collection.value = it
            }
        }
    }

    fun collectionListener() {
        viewModelScope.launch {
            repository.collectionListener().collect {
                collectionListener.value = it
            }
        }
    }
}