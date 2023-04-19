package com.example.receiptstorage.core.db.repository

import com.example.receiptstorage.core.db.entity.Receipt
import com.example.receiptstorage.core.db.entity.ReceiptMinimal
import kotlinx.coroutines.flow.Flow

interface LocalReceiptRepository {

    suspend fun getReceipts(): List<Receipt>

    suspend fun getReceiptsFlow(): Flow<List<Receipt>>

    suspend fun getMinimalReceipts(): List<ReceiptMinimal>

    suspend fun getMinimalReceiptsFlow(): Flow<List<ReceiptMinimal>>

    suspend fun getReceiptsByMonth(timestamp: Long): List<Receipt>

    suspend fun getReceiptsFlowByMonth(timestamp: Long): Flow<List<Receipt>>

    suspend fun getMinimalReceiptsByMonth(timestamp: Long): List<ReceiptMinimal>

    suspend fun getMinimalReceiptsFlowByMonth(timestamp: Long): Flow<List<ReceiptMinimal>>

    suspend fun getReceiptById(id: String): Receipt

    suspend fun getReceiptFlowById(id: String): Flow<Receipt>

    suspend fun saveReceipt(receipt: Receipt)

    suspend fun deleteReceipt(receipt: Receipt)
}
