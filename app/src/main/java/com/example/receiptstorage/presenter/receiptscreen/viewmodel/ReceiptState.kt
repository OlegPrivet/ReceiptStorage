package com.example.receiptstorage.presenter.receiptscreen.viewmodel

import com.example.receiptstorage.core.data.ReceiptUIItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ReceiptState(
    val title: String = "",
    val formattedAmount: String = "",
    val items: ImmutableList<ReceiptUIItem> = emptyList<ReceiptUIItem>().toImmutableList()
)
