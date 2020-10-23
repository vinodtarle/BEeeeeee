package com.beershop.adgaon.sales.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.beershop.adgaon.BR
import com.beershop.adgaon.base.utility.constant.BaseModule
import com.beershop.adgaon.base.utility.constant.Constant.Companion.BLANK
import com.beershop.adgaon.item.model.Item
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Sale(
    @get:Exclude override var id: String = BLANK
) : BaseModule, BaseObservable() {

    @ServerTimestamp
    @get:Bindable
    var createdAt: Date? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.createdAt)
        }

    @get:Bindable
    var createdBy: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.createdBy)
        }

    @get:Bindable
    var createdById: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.createdById)
        }

    @get:Bindable
    var selected: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.selected)
        }

    @get:Bindable
    var status: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.status)
        }

    @get:Bindable
    var hasData: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hasData)
        }

    @get:Bindable
    var number: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.number)
        }

    @get:Bindable
    var date: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var name: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var items: HashMap<String, Item>? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.items)
        }

    @get:Bindable
    var total: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.total)
        }

    @get:Bindable
    var paymentStatus: String = "Paid"
        set(value) {
            field = value
            notifyPropertyChanged(BR.paymentStatus)
        }
}