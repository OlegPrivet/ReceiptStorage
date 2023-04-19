package com.example.receiptstorage.presenter.util

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

object CurrencyFormatter {

    fun Long.shortFormat(): String {
        val bigDecimalAmount = BigDecimal(this).movePointLeft(2)
        return DecimalFormat.getCurrencyInstance(Locale.getDefault()).apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }.format(bigDecimalAmount)
    }
}
