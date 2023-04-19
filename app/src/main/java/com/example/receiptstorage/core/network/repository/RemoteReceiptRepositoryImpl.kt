package com.example.receiptstorage.core.network.repository

import com.example.receiptstorage.core.network.data.Data
import com.example.receiptstorage.core.network.data.Response
import com.example.receiptstorage.core.network.result.ReceiptFetchResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.util.reflect.*
import timber.log.Timber
import javax.inject.Inject

class RemoteReceiptRepositoryImpl @Inject constructor(
    private val client: HttpClient,
) : RemoteReceiptRepository {

    @OptIn(InternalAPI::class)
    override suspend fun getReceipt(qrRaw: String, token: String): ReceiptFetchResult =
        handleRequest {
            val remoteReceipt: Response = client.post {
                url(BASE_URL)
                contentType(ContentType.Application.FormUrlEncoded)
                body = FormDataContent(
                    Parameters.build {
                        append("qrraw", qrRaw)
                        append("token", token)
                    }
                )
            }.body()
            when (remoteReceipt.code) {
                1 -> {
                    val data = (remoteReceipt.data as Data)
                    ReceiptFetchResult.Successfully(receipt = data.json)
                }
                else -> ReceiptFetchResult.Failure(error = remoteReceipt.data as String)
            }
        }

    private suspend fun handleRequest(
        block: suspend () -> ReceiptFetchResult,
    ): ReceiptFetchResult {
        return try {
            block()
        } catch (e: RedirectResponseException) {
            // 3xx - responses
            Timber.e("Error: ${e.response.status.description}")
            ReceiptFetchResult.Failure(error = e.response.status.description)
        } catch (e: ClientRequestException) {
            // 4xx - responses
            Timber.e("Error: ${e.response.status.description}")
            ReceiptFetchResult.Failure(error = e.response.status.description)
        } catch (e: ServerResponseException) {
            // 5xx - responses
            Timber.e("Error: ${e.response.status.description}")
            ReceiptFetchResult.Failure(error = e.response.status.description)
        } catch (e: Exception) {
            Timber.e("Error: ${e.message}")
            ReceiptFetchResult.Failure(error = e.message.toString())
        }
    }

    private companion object {
        const val BASE_URL = "https://proverkacheka.com/api/v1/check/get"
    }
}
