package com.example.receiptstorage.core.network.data.serializer

import com.example.receiptstorage.core.network.data.Data
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import timber.log.Timber

object DataSerializer : KSerializer<Any> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(Data::class.java.canonicalName, PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Any) {
        when (value) {
            is String -> encoder.encodeString(value)
            is Data -> encoder.encodeSerializableValue(Data.serializer(), value)
            else -> throw SerializationException("Unsupported type for DataOrStringSerializer")
        }
    }

    override fun deserialize(decoder: Decoder): Any {
        return try {
            decoder.decodeSerializableValue(Data.serializer())
        } catch (e: Exception) {
            try {
                decoder.decodeString()
            } catch (e: Exception) {
                Timber.e("deserialize error: $e")
            }
        }
    }
}
