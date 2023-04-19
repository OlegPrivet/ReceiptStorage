package com.example.receiptstorage.core.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.receiptstorage.core.db.entity.Receipt
import com.example.receiptstorage.core.db.entity.ReceiptMinimal
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {

    @Query("SELECT * FROM Receipt")
    suspend fun getReceipts(): List<Receipt>

    @Query("SELECT * FROM Receipt")
    fun getReceiptsFlow(): Flow<List<Receipt>>

    @Query("SELECT * FROM Receipt")
    suspend fun getMinimalReceipts(): List<ReceiptMinimal>

    @Query("SELECT * FROM Receipt")
    fun getMinimalReceiptsFlow(): Flow<List<ReceiptMinimal>>

    @Query("SELECT * FROM Receipt where filterTimestamp = :timestamp")
    suspend fun getReceiptsByMonth(timestamp: Long): List<Receipt>

    @Query("SELECT * FROM Receipt where filterTimestamp = :timestamp")
    fun getReceiptsFlowByMonth(timestamp: Long): Flow<List<Receipt>>

    @Query("SELECT * FROM Receipt where filterTimestamp = :timestamp")
    suspend fun getMinimalReceiptsByMonth(timestamp: Long): List<ReceiptMinimal>

    @Query("SELECT * FROM Receipt where filterTimestamp = :timestamp")
    fun getMinimalReceiptsFlowByMonth(timestamp: Long): Flow<List<ReceiptMinimal>>

    @Query("SELECT * FROM Receipt where id = (:id)")
    suspend fun getReceiptById(id: String): Receipt

    @Query("SELECT * FROM Receipt where id = (:id)")
    fun getReceiptFlowById(id: String): Flow<Receipt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReceipt(receipt: Receipt)

    @Delete
    suspend fun deleteReceipt(receipt: Receipt)
}
