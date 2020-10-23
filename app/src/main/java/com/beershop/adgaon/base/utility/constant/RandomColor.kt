package com.beershop.adgaon.base.utility.constant

import android.content.Context
import android.graphics.Color

class RandomColor {
    companion object {
        fun getColor(context: Context, typeColor: String): Int {
            var colorCode = Color.GRAY
            val arrayId =
                context.resources.getIdentifier("color_$typeColor", "array", context.packageName)
            if (arrayId != 0) {
                val colors = context.resources.obtainTypedArray(arrayId)
                val index = (Math.random() * colors.length()).toInt()
                colorCode = colors.getColor(index, Color.GRAY)
                colors.recycle()
            }
            return colorCode
        }
    }
}