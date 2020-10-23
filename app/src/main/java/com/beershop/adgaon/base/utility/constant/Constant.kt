package com.beershop.adgaon.base.utility.constant

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import java.util.*

class Constant {
    companion object {
        const val BLANK = ""
        const val SPACE = " "
        const val DASH = "-"
        const val UNDERSCORE = "_"
        const val FIELD_SEPARATOR = "."
        const val DATE_SEPARATOR = "/"
        const val TIME_SEPARATOR = ":"
        const val FORMAT_EEEE_DD_MMM_YYYY = "EEEE dd MMM yyyy"
        const val FORMAT_DD_MMMM_YYYY = "dd MMMM yyyy"
        const val FORMAT_DD_MMM_YYYY = "dd MMM yyyy"

        const val FORMAT_DATE_DMY = "dd${DATE_SEPARATOR}MM${DATE_SEPARATOR}yyyy"
        const val FORMAT_DATE_MDY = "MM${DATE_SEPARATOR}dd${DATE_SEPARATOR}yyyy"
        const val FORMAT_DATE_YMD = "yyyy${DATE_SEPARATOR}MM${DATE_SEPARATOR}dd"
        const val FORMAT_DATE_TIME =
            "yyyy${DATE_SEPARATOR}MM${DATE_SEPARATOR}dd HH${TIME_SEPARATOR}mm${TIME_SEPARATOR}ss"
        const val FORMAT_TIME = "HH${TIME_SEPARATOR}mm"
        const val FORMAT_TIME_FULL = "HH${TIME_SEPARATOR}mm${TIME_SEPARATOR}ss"

        fun hideKeyboard(activity: Activity) {
            if (activity.currentFocus != null) {
                val manager =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
            }
        }

        fun getCurrentDate(): String {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            return "$day${DATE_SEPARATOR}${month + 1}${DATE_SEPARATOR}$year"
        }

        fun getUniqueNumber(): String {
            return Calendar.getInstance().run {
                "${get(Calendar.YEAR)}"
                    .plus("${get(Calendar.MONTH) + 1}")
                    .plus("${get(Calendar.DAY_OF_MONTH)}")
                    .plus("${get(Calendar.HOUR)}")
                    .plus("${get(Calendar.MINUTE)}")
                    .plus("${get(Calendar.SECOND)}")
                    .plus("${get(Calendar.MILLISECOND)}")
            }.toString()
        }
    }
}