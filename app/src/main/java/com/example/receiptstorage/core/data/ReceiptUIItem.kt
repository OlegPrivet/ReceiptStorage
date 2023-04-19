package com.example.receiptstorage.core.data

import com.example.receiptstorage.core.db.entity.ReceiptItem
import com.example.receiptstorage.presenter.util.CurrencyFormatter.shortFormat
import java.util.*

data class ReceiptUIItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val sum: Long,
    val formattedSum: String,
    val price: Long,
    val formattedPrice: String,
    val unit: String?,
    val quantity: Float,
)

internal val ReceiptItem.uiValue: ReceiptUIItem
    get() = ReceiptUIItem(
        name = name,
        sum = sum,
        formattedSum = sum.shortFormat(),
        price = price,
        formattedPrice = price.shortFormat(),
        unit = unit,
        quantity = quantity
    )

internal val ReceiptUIItem.storageValue: ReceiptItem
    get() = ReceiptItem(
        name = name,
        sum = sum,
        price = price,
        unit = unit,
        quantity = quantity
    )
