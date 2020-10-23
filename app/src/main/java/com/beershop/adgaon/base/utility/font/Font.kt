package com.beershop.adgaon.base.utility.font

import android.graphics.Typeface

class Font {
    companion object {
        fun getTypefaceBold() = Typeface.create("sans-serif", Typeface.BOLD)
        fun getTypefaceRegular() = Typeface.create("sans-serif", Typeface.NORMAL)
        fun getTypefaceLight() = Typeface.create("sans-serif-light", Typeface.NORMAL)
    }
}