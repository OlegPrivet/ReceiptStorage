package com.example.receiptstorage.presenter.receiptscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.receiptstorage.core.data.ReceiptUIItem
import com.example.receiptstorage.presenter.annotation.ReceiptsNavGraph
import com.example.receiptstorage.presenter.listscreen.view.ReceiptTotals
import com.example.receiptstorage.presenter.receiptscreen.viewmodel.ReceiptAction
import com.example.receiptstorage.presenter.receiptscreen.viewmodel.ReceiptScreenViewModel
import com.example.receiptstorage.presenter.ui.theme.RsTheme
import com.example.receiptstorage.presenter.ui.view.ReceiptBackdropScaffold
import com.example.receiptstorage.presenter.ui.view.Toolbar
import com.example.receiptstorage.presenter.util.remember
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@ReceiptsNavGraph
@Composable
fun ReceiptScreen(
    destinationsNavigator: DestinationsNavigator,
    receiptId: String,
    viewModel: ReceiptScreenViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = true) {
        viewModel.initialize(receiptId)
    }
    val action = viewModel.action.collectAsStateWithLifecycle()
    when (action.value) {
        is ReceiptAction.Unknown -> {}
        is ReceiptAction.Back -> destinationsNavigator.popBackStack()
    }

    val uiState = viewModel.receipt.collectAsStateWithLifecycle()
    ReceiptBackdropScaffold(
        appBar = remember {
            {
                Toolbar(
                    titleText = uiState.value.title,
                    navigationIcon = {
                        IconButton(
                            onClick = remember { { viewModel.sendAction(ReceiptAction.Back) } }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = RsTheme.colors.primaryText
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = viewModel::deleteReceipt,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete",
                                tint = RsTheme.colors.primaryText
                            )
                        }
                    }
                )
            }
        },
        backLayerContent = remember {
            {
                ReceiptTotals(totalSum = uiState.value.formattedAmount)
            }
        },
        frontLayerContent = remember {
            {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(
                        items = uiState.value.items,
                        key = { it.id }
                    ) { receipt ->
                        ReceiptItem(
                            item = receipt,
                            onItemClick = remember { {} }
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun ReceiptItem(
    modifier: Modifier = Modifier,
    item: ReceiptUIItem,
    onItemClick: (ReceiptUIItem) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(
                onClick = { onItemClick(item) }.remember(),
                indication = rememberRipple(bounded = true),
                interactionSource = MutableInteractionSource()
            )
            .background(RsTheme.colors.secondaryBackground)
    ) {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.name,
                style = RsTheme.typography.body.copy(fontSize = 14.sp),
                color = RsTheme.colors.secondaryText,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                text = "${item.formattedPrice} × ${item.quantity} ${item.unit ?: ""}",
                style = RsTheme.typography.body.copy(fontSize = 12.sp),
                color = RsTheme.colors.secondaryText.copy(alpha = 0.5f)
            )
        }
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = item.formattedSum,
                style = RsTheme.typography.body,
                color = RsTheme.colors.secondaryText,
            )
        }
    }
}
