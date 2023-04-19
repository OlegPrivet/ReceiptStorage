package com.example.receiptstorage.core.data

import com.example.receiptstorage.core.db.entity.ReceiptMinimal
import com.example.receiptstorage.presenter.util.CurrencyFormatter.shortFormat
import com.example.receiptstorage.presenter.util.DateFormatter.formatDateYYMMDDHHMM
import java.util.*

data class ReceiptUIMinimal(
    val id: String = UUID.randomUUID().toString(),
    val retailPlace: String,
    val timestamp: Long,
    val filterTimestamp: Long,
    val formattedDate: String,
    val totalAmount: Long,
    val formattedAmount: String,
)

internal val ReceiptMinimal.uiValue: ReceiptUIMinimal
    get() = ReceiptUIMinimal(
        id = id,
        retailPlace = retailPlace,
        timestamp = timestamp,
        filterTimestamp = filterTimestamp,
        formattedDate = timestamp.formatDateYYMMDDHHMM(),
        totalAmount = totalAmount,
        formattedAmount = totalAmount.shortFormat(),
    )
