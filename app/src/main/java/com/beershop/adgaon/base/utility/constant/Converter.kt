package com.beershop.adgaon.base.utility.constant

import com.beershop.adgaon.base.utility.constant.Constant.Companion.DATE_SEPARATOR
import com.beershop.adgaon.base.utility.constant.Constant.Companion.FORMAT_DATE_DMY
import com.beershop.adgaon.base.utility.constant.Constant.Companion.SPACE
import com.beershop.adgaon.base.utility.constant.Constant.Companion.TIME_SEPARATOR
import java.text.SimpleDateFormat
import java.util.*

object Converter {
    fun toYMD(day: Int, month: Int, year: Int): String {
        return year.toString() + DATE_SEPARATOR + month + DATE_SEPARATOR + day
    }

    fun toDMY(day: Int, month: Int, year: Int): String {
        return day.toString() + DATE_SEPARATOR + month + DATE_SEPARATOR + year
    }

    fun toMDY(day: Int, month: Int, year: Int): String {
        return month.toString() + DATE_SEPARATOR + day + DATE_SEPARATOR + year
    }

    fun toHM(hours: Int, minutes: Int): String {
        val tas = if (hours > 12) hours - 12 else hours
        val time = if (hours >= 12) "PM" else "AM"
        return tas.toString() + TIME_SEPARATOR + minutes + SPACE + time
    }

    fun toHMS(hours: Int, minutes: Int, seconds: Int): String {
        return hours.toString() + TIME_SEPARATOR + minutes + TIME_SEPARATOR + seconds
    }

    fun toString(date: Date): String {
        return SimpleDateFormat(FORMAT_DATE_DMY).format(date)
    }

    fun toDate(date: String): Date {
        return SimpleDateFormat(FORMAT_DATE_DMY).parse(date)
    }

    fun toDate(date: Date?): Date {
        val format = SimpleDateFormat(FORMAT_DATE_DMY).format(date)
        return SimpleDateFormat(FORMAT_DATE_DMY).parse(format)
    }
}