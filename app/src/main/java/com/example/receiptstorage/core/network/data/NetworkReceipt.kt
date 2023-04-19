package com.example.receiptstorage.core.network.data

import com.example.receiptstorage.core.db.entity.Receipt
import com.example.receiptstorage.presenter.util.DateFormatter.parseStringToMilliseconds
import com.example.receiptstorage.presenter.util.DateFormatter.parseStringToShortMilliseconds
import kotlinx.serialization.Serializable

@Serializable
data class NetworkReceipt(
    val retailPlace: String,
    val totalSum: Long,
    val dateTime: String,
    val items: List<NetworkReceiptItem>
)

internal val NetworkReceipt.storageValue: Receipt
    get() = Receipt(
        retailPlace = retailPlace,
        timestamp = dateTime.parseStringToMilliseconds(),
        filterTimestamp = dateTime.parseStringToShortMilliseconds(),
        totalAmount = totalSum,
        items = items.map { it.storageValue }
    )
