package com.example.receiptstorage.core.db.converter

import androidx.room.TypeConverter
import com.example.receiptstorage.core.db.entity.ReceiptItem
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromString(value: String): List<ReceiptItem> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromArrayList(list: List<ReceiptItem>): String {
        return Json.encodeToString(list)
    }
}
