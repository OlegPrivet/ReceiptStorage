package com.example.receiptstorage.core.network.data

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val json: NetworkReceipt
)
