package com.beershop.adgaon.sales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beershop.adgaon.databinding.ViewSaleItemBinding
import com.beershop.adgaon.item.model.Item
import javax.inject.Inject

class AdapterSaleItem @Inject internal constructor(
) : RecyclerView.Adapter<AdapterSaleItem.ViewHolder>() {
    private var collection = emptyList<Item>()

    inner class ViewHolder(val binding: ViewSaleItemBinding) :
        RecyclerView.ViewHolder(binding.layoutMain)

    internal fun setCollection(collection: List<Item>) {
        this.collection = collection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewSaleItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = collection[position]
        holder.binding.item = document
    }

    override fun getItemCount(): Int {
        return this.collection.size
    }
}