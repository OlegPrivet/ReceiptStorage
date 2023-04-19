package com.example.receiptstorage.presenter.listscreen.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.receiptstorage.R
import com.example.receiptstorage.core.data.Filter
import com.example.receiptstorage.core.data.ReceiptUIMinimal
import com.example.receiptstorage.presenter.ui.theme.RsTheme
import com.example.receiptstorage.presenter.util.remember
import kotlinx.collections.immutable.ImmutableList


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReceiptList(
    modifier: Modifier,
    dates: ImmutableList<Filter>,
    receipts: ImmutableList<ReceiptUIMinimal>,
    dateOnClick: (Filter) -> Unit,
    receiptOnClick: (String) -> Unit,
) {
    if (dates.isEmpty()) return

    var listFilter by remember { mutableStateOf(0L) }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(start = 8.dp, end = 8.dp)
        ) {
            items(
                items = dates,
                key = { it.timestamp }
            ) { filter ->
                Chip(
                    modifier = modifier.width(80.dp),
                    onClick = {
                        listFilter = filter.timestamp
                        dateOnClick(filter)
                    }.remember(),
                    colors = when (listFilter == filter.timestamp) {
                        true -> ChipDefaults.chipColors(backgroundColor = RsTheme.colors.tintColor)
                        false -> ChipDefaults.chipColors(backgroundColor = Color.LightGray)
                    }
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = when (filter) {
                            is Filter.All -> stringResource(R.string.filter_all)
                            is Filter.ByMonth -> filter.formattedDate
                        },
                        style = RsTheme.typography.body,
                        color = RsTheme.colors.primaryText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                items = receipts,
                key = { it.id }
            ) { receipt ->
                ReceiptItem(
                    item = receipt,
                    onItemClick = receiptOnClick
                )
            }
        }
    }
}

@Composable
private fun ReceiptItem(
    modifier: Modifier = Modifier,
    item: ReceiptUIMinimal,
    onItemClick: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(
                onClick = { onItemClick(item.id) }.remember(),
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
                text = item.retailPlace,
                style = RsTheme.typography.body,
                color = RsTheme.colors.secondaryText,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Text(
                text = item.formattedDate,
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
                text = item.formattedAmount,
                style = RsTheme.typography.body,
                color = RsTheme.colors.secondaryText
            )
        }
    }
}
