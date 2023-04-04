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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.receiptstorage.presenter.annotation.ReceiptsNavGraph
import com.example.receiptstorage.presenter.listscreen.view.ReceiptTotals
import com.example.receiptstorage.presenter.ui.theme.RsTheme
import com.example.receiptstorage.presenter.ui.view.ReceiptBackdropScaffold
import com.example.receiptstorage.presenter.ui.view.Toolbar
import com.example.receiptstorage.presenter.util.remember
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.toImmutableList

@Destination
@ReceiptsNavGraph
@Composable
fun ReceiptScreen(
    destinationsNavigator: DestinationsNavigator,
) {
    ReceiptBackdropScaffold(
        appBar = remember {
            {
                Toolbar(
                    titleText = "Name of shop",
                    navigationIcon = {
                        IconButton(
                            onClick = remember { { destinationsNavigator.navigateUp() } }
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
                            onClick = { }.remember(),
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
                ReceiptTotals(totalSum = "$ 23 093.20")
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
                        items = listOf(
                            "All",
                            "03.23",
                            "04.23",
                            "05.23",
                            "06.23",
                            "07.23",
                            "08.23",
                            "09.23",
                            "10.23",
                            "All1",
                            "03.231",
                            "04.231",
                            "05.231",
                            "06.231",
                            "07.231",
                            "08.231",
                            "09.231",
                            "10.231"
                        ).toImmutableList(),
                        key = { it }
                    ) { receipt ->
                        ReceiptItem(
                            item = receipt,
                            onItemClick = { }
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
    item: String,
    onItemClick: (String) -> Unit,
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
                text = item,
                style = RsTheme.typography.body,
                color = RsTheme.colors.secondaryText
            )
            Text(
                text = "1 шт.",
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
                text = "$ 200.00",
                style = RsTheme.typography.body,
                color = RsTheme.colors.secondaryText
            )
        }
    }
}
