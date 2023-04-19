package com.example.receiptstorage.presenter.qrscanner.state

sealed interface QrScannerScreenState {
    object Scanning : QrScannerScreenState
    object FetchingData : QrScannerScreenState
    sealed interface ShowingResult : QrScannerScreenState {
        data class ShowSuccess(val receiptId: String) : ShowingResult
        data class ShowError(val error: String) : ShowingResult
    }
}
