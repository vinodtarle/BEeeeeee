package com.beershop.adgaon.stock.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.beershop.adgaon.BR
import com.beershop.adgaon.base.utility.constant.BaseModule
import com.beershop.adgaon.base.utility.constant.Constant.Companion.BLANK
import com.beershop.adgaon.item.model.Item
import com.beershop.adgaon.supplier.model.Supplier
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Stock(
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
    var date: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var supplier: Supplier? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.supplier)
        }

    @get:Bindable
    var item: Item? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.item)
        }

    @get:Bindable
    var quantity: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.quantity)
        }

    @get:Bindable
    var mrp: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.mrp)
        }

    @get:Bindable
    var price: String = BLANK
        set(value) {
            field = value
            notifyPropertyChanged(BR.price)
        }
}