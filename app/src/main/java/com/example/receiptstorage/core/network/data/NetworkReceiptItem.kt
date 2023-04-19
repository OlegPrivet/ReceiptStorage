package com.example.receiptstorage.core.network.data

import com.example.receiptstorage.core.db.entity.ReceiptItem
import kotlinx.serialization.Serializable

@Serializable
data class NetworkReceiptItem(
    val sum: Long,
    val name: String,
    val price: Long,
    val quantity: Float,
)

val NetworkReceiptItem.storageValue: ReceiptItem
    get() = ReceiptItem(
        name = name,
        price = price,
        sum = sum,
        unit = null,
        quantity = quantity
    )
