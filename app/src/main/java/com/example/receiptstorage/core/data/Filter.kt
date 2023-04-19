package com.example.receiptstorage.core.data

sealed class Filter(val timestamp: Long) {
    object All: Filter(timestamp = 0L)
    data class ByMonth(val timestampNew: Long, val formattedDate: String): Filter(timestamp = timestampNew)
}
