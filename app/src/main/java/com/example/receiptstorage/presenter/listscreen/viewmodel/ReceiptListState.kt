package com.example.receiptstorage.presenter.listscreen.viewmodel

import com.example.receiptstorage.core.data.Filter
import com.example.receiptstorage.core.data.ReceiptUIMinimal
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class ReceiptListState(
    val totalFormattedSum: String = "",
    val receipts: ImmutableList<ReceiptUIMinimal> = emptyList<ReceiptUIMinimal>().toImmutableList(),
    val receiptsFilter: ImmutableList<Filter> = emptyList<Filter>().toImmutableList(),
)
