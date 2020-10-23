package com.beershop.adgaon.base.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.beershop.adgaon.BR
import com.beershop.adgaon.base.utility.constant.Constant.Companion.BLANK
import com.google.firebase.firestore.ServerTimestamp

class UI : BaseObservable() {
    @ServerTimestamp
    @get:Bindable
    var date: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @ServerTimestamp
    @get:Bindable
    var time: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.time)
        }

    @get:Bindable
    var update: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.update)
        }
}