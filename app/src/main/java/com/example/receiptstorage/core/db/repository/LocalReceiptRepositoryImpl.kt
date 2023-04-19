package com.example.receiptstorage.core.db.repository

import com.example.receiptstorage.core.db.ReceiptDatabase
import com.example.receiptstorage.core.db.entity.Receipt
import com.example.receiptstorage.core.db.entity.ReceiptMinimal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalReceiptRepositoryImpl @Inject constructor(database: ReceiptDatabase) : LocalReceiptRepository {

    private val receiptDao = database.receiptDao()

    override suspend fun getReceipts(): List<Receipt> =
        receiptDao.getReceipts()

    override suspend fun getReceiptsFlow(): Flow<List<Receipt>> =
        receiptDao.getReceiptsFlow()


    override suspend fun getMinimalReceipts(): List<ReceiptMinimal> =
        receiptDao.getMinimalReceipts()


    override suspend fun getMinimalReceiptsFlow(): Flow<List<ReceiptMinimal>> =
        receiptDao.getMinimalReceiptsFlow()


    override suspend fun getReceiptsByMonth(timestamp: Long): List<Receipt> =
        receiptDao.getReceiptsByMonth(timestamp)


    override suspend fun getReceiptsFlowByMonth(timestamp: Long): Flow<List<Receipt>> =
        receiptDao.getReceiptsFlowByMonth(timestamp)


    override suspend fun getMinimalReceiptsByMonth(timestamp: Long): List<ReceiptMinimal> =
        receiptDao.getMinimalReceiptsByMonth(timestamp)


    override suspend fun getMinimalReceiptsFlowByMonth(timestamp: Long): Flow<List<ReceiptMinimal>> =
        receiptDao.getMinimalReceiptsFlowByMonth(timestamp)


    override suspend fun getReceiptById(id: String): Receipt =
        receiptDao.getReceiptById(id)


    override suspend fun getReceiptFlowById(id: String): Flow<Receipt> =
        receiptDao.getReceiptFlowById(id)


    override suspend fun saveReceipt(receipt: Receipt) {
        receiptDao.saveReceipt(receipt)
    }

    override suspend fun deleteReceipt(receipt: Receipt) {
        receiptDao.deleteReceipt(receipt)
    }
}
