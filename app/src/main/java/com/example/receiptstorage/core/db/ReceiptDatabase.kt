package com.example.receiptstorage.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.receiptstorage.core.db.converter.Converters
import com.example.receiptstorage.core.db.dao.ReceiptDao
import com.example.receiptstorage.core.db.entity.Receipt
import timber.log.Timber

@Database(
    entities = [Receipt::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class ReceiptDatabase : RoomDatabase() {

    abstract fun receiptDao(): ReceiptDao

    companion object {

        private const val DB_NAME = "receipts.db"

        @Volatile
        private var instance: ReceiptDatabase? = null

        operator fun invoke(context: Context): ReceiptDatabase {
            return instance ?: synchronized(ReceiptDatabase::class) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        @Suppress("SpreadOperator")
        @Synchronized
        private fun buildDatabase(context: Context): ReceiptDatabase =
            Room.databaseBuilder(context.applicationContext, ReceiptDatabase::class.java, DB_NAME)
                .addCallback(callbackDb())
                .build()

        private fun callbackDb() = object : Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                Timber.d("DB.onCreate")
                super.onCreate(db)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                Timber.d("DB.onOpen")
                super.onOpen(db)
            }
        }
    }
}
