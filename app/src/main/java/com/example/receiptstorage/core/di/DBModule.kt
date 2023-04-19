package com.example.receiptstorage.core.di

import android.content.Context
import com.example.receiptstorage.core.db.ReceiptDatabase
import com.example.receiptstorage.core.db.repository.LocalReceiptRepository
import com.example.receiptstorage.core.db.repository.LocalReceiptRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DBModule {

    @Provides
    fun provideReceiptRepository(receiptDatabase: ReceiptDatabase): LocalReceiptRepository =
        LocalReceiptRepositoryImpl(receiptDatabase)

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): ReceiptDatabase = ReceiptDatabase(context)
}
