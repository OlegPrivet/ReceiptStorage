package com.example.receiptstorage.presenter.qrscanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receiptstorage.BuildConfig
import com.example.receiptstorage.core.db.repository.LocalReceiptRepository
import com.example.receiptstorage.core.network.data.storageValue
import com.example.receiptstorage.core.network.repository.RemoteReceiptRepository
import com.example.receiptstorage.core.network.result.ReceiptFetchResult
import com.example.receiptstorage.presenter.qrscanner.action.QrScannerAction
import com.example.receiptstorage.presenter.qrscanner.state.QrScannerScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(
    private val remoteReceiptRepository: RemoteReceiptRepository,
    private val localReceiptRepository: LocalReceiptRepository,
) : ViewModel() {

    private val _state = MutableStateFlow<QrScannerScreenState>(QrScannerScreenState.Scanning)
    val uiState = _state.asStateFlow()

    fun setAction(action: QrScannerAction) {
        when (action) {
            is QrScannerAction.ScanQr -> updateState(QrScannerScreenState.Scanning)
            is QrScannerAction.FetchData -> fetchData(action.qrCode)
        }
    }

    @Suppress("MoveVariableDeclarationIntoWhen")
    private fun fetchData(qrRaw: String) {
        updateState(QrScannerScreenState.FetchingData)
        viewModelScope.launch(Dispatchers.IO) {
            val result = remoteReceiptRepository.getReceipt(qrRaw, BuildConfig.ACCESS_TOKEN)
            when (result) {
                is ReceiptFetchResult.Failure -> {
                    updateState(QrScannerScreenState.ShowingResult.ShowError(result.error))
                }
                is ReceiptFetchResult.Successfully -> {
                    val storageReceipt = result.receipt.storageValue
                    localReceiptRepository.saveReceipt(storageReceipt)
                    updateState(QrScannerScreenState.ShowingResult.ShowSuccess(receiptId = storageReceipt.id))
                }
            }
        }
    }

    private fun updateState(newState: QrScannerScreenState) {
        _state.value = newState
    }
}
