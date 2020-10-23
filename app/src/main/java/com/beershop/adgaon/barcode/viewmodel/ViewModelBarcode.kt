package com.beershop.adgaon.barcode.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beershop.adgaon.base.utility.constant.Constant.Companion.BLANK

class ViewModelBarcode @ViewModelInject internal constructor() : ViewModel() {
    val barcode = MutableLiveData<String>()

    fun barcode(value: String = BLANK) {
        barcode.value = value
    }
}