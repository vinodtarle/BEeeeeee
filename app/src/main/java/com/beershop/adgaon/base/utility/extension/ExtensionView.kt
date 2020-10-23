package com.beershop.adgaon.base.utility.extension

import android.view.View

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide(vararg: View) {
    vararg
}
