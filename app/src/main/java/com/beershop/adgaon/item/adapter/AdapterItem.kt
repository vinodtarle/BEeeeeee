package com.beershop.adgaon.item.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beershop.adgaon.databinding.ViewItemBinding
import com.beershop.adgaon.item.model.Item
import javax.inject.Inject

class AdapterItem @Inject internal constructor(
) : RecyclerView.Adapter<AdapterItem.ViewHolder>() {
    private var collection = emptyList<Item>()

    inner class ViewHolder(val binding: ViewItemBinding) :
        RecyclerView.ViewHolder(binding.layoutMain)

    internal fun setCollection(collection: List<Item>) {
        this.collection = collection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = this.collection[position]
        holder.binding.item = document
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