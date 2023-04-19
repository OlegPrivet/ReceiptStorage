package com.example.receiptstorage.core.db.entity

import java.util.*

data class ReceiptMinimal(
    val id: String = UUID.randomUUID().toString(),
    val retailPlace: String,
    val timestamp: Long,
    val filterTimestamp: Long,
    val totalAmount: Long,
)
