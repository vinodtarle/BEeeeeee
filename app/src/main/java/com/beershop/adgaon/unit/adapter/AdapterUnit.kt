package com.beershop.adgaon.unit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beershop.adgaon.databinding.ViewUnitBinding
import com.beershop.adgaon.unit.model.Unit
import javax.inject.Inject

class AdapterUnit @Inject internal constructor(
) : RecyclerView.Adapter<AdapterUnit.ViewHolder>() {
    private var collection = emptyList<Unit>()

    inner class ViewHolder(val binding: ViewUnitBinding) :
        RecyclerView.ViewHolder(binding.layoutMain)

    internal fun setCollection(collection: List<Unit>) {
        this.collection = collection
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewUnitBinding.inflate(inflater, parent, false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val document = collection[position]
        holder.binding.unit = document
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