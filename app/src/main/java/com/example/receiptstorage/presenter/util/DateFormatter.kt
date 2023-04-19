package com.example.receiptstorage.presenter.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    private val locale = Locale.getDefault()

    /**
     * Format [MM.yy]
     */
    @Synchronized
    fun Long.formatDateMMYY(): String = Date(this).format("MM.yy")

    /**
     * Format [dd.MM.yy]
     */
    @Synchronized
    fun Long.formatDateYYMMDD(): String = Date(this).format("dd.MM.yy")

    /**
     * Format [dd.MM.yy hh:mm]
     */
    @Synchronized
    fun Long.formatDateYYMMDDHHMM(): String = Date(this).format("dd.MM.yy hh:mm")

    /**
     * Parse format [yyyy-MM-dd'T'HH:mm:ss]
     */
    @Synchronized
    fun String.parseStringToMilliseconds(): Long =
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale).parse(this)!!.time

    /**
     * Parse format [yyyy-MM-dd'T'HH:mm:ss] to yy.mm time
     */
    @Synchronized
    fun String.parseStringToShortMilliseconds(): Long {
        val time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale).parse(this)!!.time
        return time.resetDate()
    }

    @Synchronized
    fun Long.resetDate(): Long =
        Calendar.getInstance().apply {
            timeInMillis = this@resetDate
            set(Calendar.DAY_OF_MONTH, 1)
            val month = get(Calendar.MONTH)
            val year = get(Calendar.YEAR)

            set(year, month, 1, 0, 0, 0)
        }.timeInMillis

    private fun Date.format(pattern: String) = SimpleDateFormat(pattern, locale).format(this)
}
