package com.example.receiptstorage.presenter.listscreen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.receiptstorage.R
import com.example.receiptstorage.presenter.annotation.ReceiptsNavGraph
import com.example.receiptstorage.presenter.destinations.QrScannerScreenDestination
import com.example.receiptstorage.presenter.destinations.ReceiptScreenDestination
import com.example.receiptstorage.presenter.listscreen.view.ReceiptList
import com.example.receiptstorage.presenter.listscreen.view.ReceiptTotals
import com.example.receiptstorage.presenter.listscreen.viewmodel.ReceiptListViewModel
import com.example.receiptstorage.presenter.ui.theme.RsTheme
import com.example.receiptstorage.presenter.ui.view.ReceiptBackdropScaffold
import com.example.receiptstorage.presenter.ui.view.Toolbar
import com.example.receiptstorage.presenter.util.remember
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ReceiptsNavGraph(start = true)
@Destination
@Composable
fun ReceiptListScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: ReceiptListViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.initialize()
    }
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    ReceiptBackdropScaffold(
        appBar = remember {
            {
                Toolbar(
                    titleText = stringResource(id = R.string.app_name),
                    actions = {
                        IconButton(
                            onClick = { destinationsNavigator.navigate(QrScannerScreenDestination) }.remember(),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add",
                                tint = RsTheme.colors.primaryText
                            )
                        }
                    }
                )
            }
        },
        backLayerContent = remember {
            {
                ReceiptTotals(totalSum = uiState.value.totalFormattedSum)
            }
        },
        frontLayerContent = remember {
            {
                ReceiptList(
                    modifier = Modifier,
                    dates = uiState.value.receiptsFilter,
                    receipts = uiState.value.receipts,
                    dateOnClick = viewModel::showReceiptsByFilter,
                    receiptOnClick = remember {
                        {
                            destinationsNavigator.navigate(ReceiptScreenDestination(receiptId = it))
                        }
                    },
                )
            }
        },
    )
}
