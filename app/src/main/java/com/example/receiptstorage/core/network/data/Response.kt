package com.example.receiptstorage.core.network.data

import com.example.receiptstorage.core.network.data.serializer.DataSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Response(
    val code: Int,
    @Serializable(with = DataSerializer::class)
    val data: Any
)
