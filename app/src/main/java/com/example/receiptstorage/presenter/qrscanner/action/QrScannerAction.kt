package com.example.receiptstorage.presenter.qrscanner.action

sealed interface QrScannerAction {
    object ScanQr : QrScannerAction
    data class FetchData(val qrCode: String) : QrScannerAction
}
