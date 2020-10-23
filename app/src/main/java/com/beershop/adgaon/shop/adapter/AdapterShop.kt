package com.beershop.adgaon.shop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beershop.adgaon.databinding.ViewShopBinding
import com.beershop.adgaon.shop.model.Shop
import javax.inject.Inject

class AdapterShop @Inject internal constructor(
) : RecyclerView.Adapter<AdapterShop.ViewHolder>() {
    private var collection = emptyList<Shop>()

    inner class ViewHolder(val binding: ViewShopBinding) :
        RecyclerView.ViewHolder(binding.layoutMain)

    internal fun setCollection(collection: List<Shop>) {
        this.collection = collection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewShopBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = collection[position]
        holder.binding.shop = document
        /* holder.binding.layoutMain.setOnLongClickListener {
             if (this.context is ActivityTransaction)
                 this.context.deleteDialog(data)
             true
         }*/
    }

    override fun getItemCount(): Int {
        return this.collection.size
    }
}