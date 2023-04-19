package com.example.receiptstorage.core.data

import com.example.receiptstorage.core.db.entity.Receipt
import com.example.receiptstorage.presenter.util.CurrencyFormatter.shortFormat
import com.example.receiptstorage.presenter.util.DateFormatter.formatDateYYMMDDHHMM
import java.util.*

data class ReceiptUI(
    val id: String = UUID.randomUUID().toString(),
    val retailPlace: String,
    val timestamp: Long,
    val filterTimestamp: Long,
    val formattedDate: String,
    val totalAmount: Long,
    val formattedAmount: String,
    val items: List<ReceiptUIItem>
)

internal val Receipt.uiValue: ReceiptUI
    get() = ReceiptUI(
        id = id,
        retailPlace = retailPlace,
        timestamp = timestamp,
        filterTimestamp = filterTimestamp,
        formattedDate = timestamp.formatDateYYMMDDHHMM(),
        totalAmount = totalAmount,
        formattedAmount = totalAmount.shortFormat(),
        items = items.map { it.uiValue }
    )

internal val ReceiptUI.storageValue: Receipt
    get() = Receipt(
        id = id,
        retailPlace = retailPlace,
        timestamp = timestamp,
        filterTimestamp = filterTimestamp,
        totalAmount = totalAmount,
        items = items.map { it.storageValue }
    )
