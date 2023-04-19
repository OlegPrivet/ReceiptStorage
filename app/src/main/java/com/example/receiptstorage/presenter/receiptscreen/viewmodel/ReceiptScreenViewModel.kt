package com.example.receiptstorage.presenter.receiptscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receiptstorage.core.data.ReceiptUI
import com.example.receiptstorage.core.data.storageValue
import com.example.receiptstorage.core.data.uiValue
import com.example.receiptstorage.core.db.repository.LocalReceiptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptScreenViewModel @Inject constructor(
    private val localReceiptRepository: LocalReceiptRepository,
) : ViewModel() {

    private val _receipt = MutableStateFlow(ReceiptState())
    val receipt = _receipt.asStateFlow()

    private val _action = MutableStateFlow<ReceiptAction>(ReceiptAction.Unknown)
    val action = _action.asStateFlow()

    private lateinit var receiptUI: ReceiptUI

    @Suppress("MoveVariableDeclarationIntoWhen")
    fun initialize(receiptId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            receiptUI = localReceiptRepository.getReceiptById(receiptId).uiValue
            _receipt.update {
                it.copy(
                    title = receiptUI.retailPlace,
                    formattedAmount = receiptUI.formattedAmount,
                    items = receiptUI.items.toImmutableList()
                )
            }
        }
    }

    fun deleteReceipt() {
        viewModelScope.launch(Dispatchers.IO) {
            localReceiptRepository.deleteReceipt(receiptUI.storageValue)
            _action.value = ReceiptAction.Back
        }
    }

    fun sendAction(action: ReceiptAction) {
        _action.value = action
    }
}
