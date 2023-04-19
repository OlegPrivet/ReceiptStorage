package com.example.receiptstorage.core.network.repository

import com.example.receiptstorage.core.network.result.ReceiptFetchResult

interface RemoteReceiptRepository {

    suspend fun getReceipt(qrRaw: String, token: String): ReceiptFetchResult
}
