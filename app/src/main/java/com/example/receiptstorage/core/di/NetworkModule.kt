package com.example.receiptstorage.core.di

import com.example.receiptstorage.core.network.repository.RemoteReceiptRepository
import com.example.receiptstorage.core.network.repository.RemoteReceiptRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = CustomLogger()
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(
        httpClient: HttpClient,
    ): RemoteReceiptRepository = RemoteReceiptRepositoryImpl(httpClient)
}

class CustomLogger : Logger {
    override fun log(message: String) {
        Timber.w(message)
    }
}
