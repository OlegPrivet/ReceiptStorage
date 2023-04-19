package com.example.receiptstorage.presenter.listscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.receiptstorage.core.data.Filter
import com.example.receiptstorage.core.data.ReceiptUIMinimal
import com.example.receiptstorage.core.data.uiValue
import com.example.receiptstorage.core.db.repository.LocalReceiptRepository
import com.example.receiptstorage.presenter.util.CurrencyFormatter.shortFormat
import com.example.receiptstorage.presenter.util.DateFormatter.formatDateMMYY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptListViewModel @Inject constructor(
    private val receiptRepository: LocalReceiptRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReceiptListState())
    val uiState = _uiState.asStateFlow()

    fun initialize() {
        viewModelScope.launch {
            val receipts = receiptRepository.getMinimalReceipts()
            if (receipts.isEmpty()) return@launch

            val filterList: MutableList<Filter> = mutableListOf(Filter.All)
            val filters = receipts
                .asSequence()
                .map { it.filterTimestamp }
                .toSet()
                .sortedByDescending { it }
                .mapTo(filterList) { Filter.ByMonth(it, it.formatDateMMYY()) }
                .toImmutableList()

            _uiState.update { state ->
                state.copy(
                    totalFormattedSum = receipts.sumOf { it.totalAmount }.shortFormat(),
                    receipts = receipts.map { it.uiValue }.sortedByDescending { it.timestamp }.toImmutableList(),
                    receiptsFilter = filters,
                )
            }
        }
    }

    fun showReceiptsByFilter(filter: Filter) {
        viewModelScope.launch {
            val receipts = when (filter) {
                is Filter.All -> receiptRepository.getMinimalReceipts()
                is Filter.ByMonth -> receiptRepository.getMinimalReceiptsByMonth(filter.timestamp)
            }
            updateReceiptsState(
                receipts = receipts.map { it.uiValue }.sortedByDescending { it.timestamp }.toImmutableList()
            )
        }
    }

    private fun updateReceiptsState(receipts: ImmutableList<ReceiptUIMinimal>) {
        _uiState.update { state ->
            state.copy(
                totalFormattedSum = receipts.sumOf { it.totalAmount }.shortFormat(),
                receipts = receipts
            )
        }
    }
}
