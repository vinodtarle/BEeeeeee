package com.beershop.adgaon.sales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beershop.adgaon.databinding.ViewSaleBinding
import com.beershop.adgaon.sales.model.Sale
import javax.inject.Inject

class AdapterSale @Inject internal constructor(
) : RecyclerView.Adapter<AdapterSale.ViewHolder>() {
    private var collection = emptyList<Sale>()

    var itemClick: (document: Sale) -> Unit = {}

    inner class ViewHolder(val binding: ViewSaleBinding) :
        RecyclerView.ViewHolder(binding.layoutMain)

    internal fun setCollection(collection: List<Sale>) {
        this.collection = collection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewSaleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = collection[position]
        holder.binding.sale = document
        holder.binding.layoutMain.setOnClickListener {
            itemClick.invoke(document)
        }
    }

    override fun getItemCount(): Int {
        return this.collection.size
    }
}