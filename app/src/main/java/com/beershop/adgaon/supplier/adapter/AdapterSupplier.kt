package com.beershop.adgaon.supplier.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beershop.adgaon.databinding.ViewSupplierBinding
import com.beershop.adgaon.supplier.model.Supplier
import javax.inject.Inject

class AdapterSupplier @Inject internal constructor(
) : RecyclerView.Adapter<AdapterSupplier.ViewHolder>() {
    private var collection = emptyList<Supplier>()

    inner class ViewHolder(val binding: ViewSupplierBinding) :
        RecyclerView.ViewHolder(binding.layoutMain)

    internal fun setCollection(collection: List<Supplier>) {
        this.collection = collection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewSupplierBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = collection[position]
        holder.binding.supplier = document
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