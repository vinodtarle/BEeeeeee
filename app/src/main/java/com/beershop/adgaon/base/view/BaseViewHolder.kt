package com.beershop.adgaon.base.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder(
    baseFragment: BaseFragment,
    view: View
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(document: Any)
}