package com.example.receiptstorage.listscreen.view

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.receiptstorage.ui.theme.ReceiptStorageTheme
import com.example.receiptstorage.ui.theme.RsTheme
import com.example.receiptstorage.util.remember
import kotlinx.collections.immutable.ImmutableList


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReceiptList(
    modifier: Modifier,
    dates: ImmutableList<String>,
    receipts: ImmutableList<String>,
    dateOnClick: (String) -> Unit,
    receiptOnClick: (String) -> Unit,
) {
    var listFilter by remember { mutableStateOf("All") }
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
                key = { it }
            ) { date ->
                Chip(
                    modifier = modifier.width(80.dp),
                    onClick = {
                        listFilter = date
                        dateOnClick(date)
                    }.remember(),
                    colors = if (listFilter == date) ChipDefaults.chipColors(backgroundColor = RsTheme.colors.tintColor)
                    else ChipDefaults.chipColors(backgroundColor = Color.LightGray)
                ) {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        text = date,
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
                key = { it }
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
                text = "01.01.1970",
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

@Preview
@Composable
private fun ReceiptItemPreview() {
    ReceiptStorageTheme {
        ReceiptItem(item = "Лента", onItemClick = {})
    }
}

@Preview
@Composable
private fun ReceiptItemPreviewDark() {
    ReceiptStorageTheme(darkTheme = true) {
        ReceiptItem(item = "Лента", onItemClick = {})
    }
}
