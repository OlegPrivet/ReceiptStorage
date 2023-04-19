package com.example.receiptstorage.presenter.receiptscreen.viewmodel

sealed class ReceiptAction {
    object Unknown : ReceiptAction()
    object Back : ReceiptAction()
}
