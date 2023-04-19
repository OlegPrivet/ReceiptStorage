package com.example.receiptstorage.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Receipt")
data class Receipt(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val retailPlace: String,
    val timestamp: Long,
    val filterTimestamp: Long,
    val totalAmount: Long,
    val items: List<ReceiptItem>
)
