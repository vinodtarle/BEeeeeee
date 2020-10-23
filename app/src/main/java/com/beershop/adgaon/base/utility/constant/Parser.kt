package com.beershop.adgaon.base.utility.constant

import com.beershop.adgaon.base.utility.constant.Constant.Companion.BLANK
import com.beershop.adgaon.base.utility.constant.Constant.Companion.SPACE
import java.text.SimpleDateFormat
import java.util.*

class Parser {
    companion object {
        fun toTime(time: String): String {
            val v = time.split(SPACE.toRegex())
            return v[0]
        }

        fun toTimeFormat(time: String): String {
            val v = time.split(SPACE.toRegex())
            return if (v.size > 1) v[1] else v[0]
        }

        fun toEDMY(date: Date): String {
            return SimpleDateFormat(Constant.FORMAT_EEEE_DD_MMM_YYYY).format(date)
        }

        fun toDMY(date: Date): String {
            return SimpleDateFormat(Constant.FORMAT_DD_MMM_YYYY).format(date)
        }

        fun day(date: Date): String {
            val data = SimpleDateFormat(Constant.FORMAT_DD_MMMM_YYYY)
                .format(date)
                .split(SPACE.toRegex())
            return if (data.isNotEmpty()) data[0] else BLANK
        }

        fun month(date: Date): String {
            val data = SimpleDateFormat(Constant.FORMAT_DD_MMMM_YYYY)
                .format(date)
                .split(SPACE.toRegex())
            return if (data.size > 1) data[1] else BLANK
        }

        fun year(date: Date): String {
            val data = SimpleDateFormat(Constant.FORMAT_DD_MMMM_YYYY)
                .format(date)
                .split(SPACE.toRegex())
            return if (data.size > 2) data[2] else BLANK
        }
    }
}