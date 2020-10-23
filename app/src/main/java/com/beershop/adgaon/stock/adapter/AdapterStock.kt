package com.beershop.adgaon.stock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beershop.adgaon.databinding.ViewStockBinding
import com.beershop.adgaon.stock.model.Stock
import javax.inject.Inject

class AdapterStock @Inject internal constructor(
) : RecyclerView.Adapter<AdapterStock.ViewHolder>() {
    private var collection = emptyList<Stock>()

    inner class ViewHolder(val binding: ViewStockBinding) :
        RecyclerView.ViewHolder(binding.layoutMain)

    internal fun setCollection(collection: List<Stock>) {
        this.collection = collection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewStockBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = collection[position]
        holder.binding.stock = document
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