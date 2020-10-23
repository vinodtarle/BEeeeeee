package com.beershop.adgaon.base.di

import com.beershop.adgaon.item.adapter.AdapterItem
import com.beershop.adgaon.sales.adapter.AdapterSale
import com.beershop.adgaon.sales.adapter.AdapterSaleItem
import com.beershop.adgaon.shop.adapter.AdapterShop
import com.beershop.adgaon.stock.adapter.AdapterStock
import com.beershop.adgaon.supplier.adapter.AdapterSupplier
import com.beershop.adgaon.unit.adapter.AdapterUnit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ModuleAdapter {

    @Singleton
    @Provides
    fun adapterShop() = AdapterShop()

    @Singleton
    @Provides
    fun adapterUnit() = AdapterUnit()

    @Singleton
    @Provides
    fun adapterItem() = AdapterItem()

    @Singleton
    @Provides
    fun adapterSupplier() = AdapterSupplier()

    @Singleton
    @Provides
    fun adapterStock() = AdapterStock()

    @Singleton
    @Provides
    fun adapterSaleItem() = AdapterSaleItem()

    @Singleton
    @Provides
    fun adapterSale() = AdapterSale()

}