package com.example.receiptstorage.core.network.result

import com.example.receiptstorage.core.network.data.NetworkReceipt

sealed class ReceiptFetchResult {

    data class Successfully(val receipt: NetworkReceipt): ReceiptFetchResult()

    data class Failure(val error: String): ReceiptFetchResult()
}
