package com.example.receiptstorage.core.db.entity

import kotlinx.serialization.Serializable

@Serializable
data class ReceiptItem(
    val name: String,
    val price: Long,
    val sum: Long,
    val unit: String?,
    val quantity: Float,
)
