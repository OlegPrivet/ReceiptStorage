package com.example.receiptstorage.presenter.listscreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.receiptstorage.R
import com.example.receiptstorage.presenter.ui.theme.RsTheme

@Composable
fun ReceiptTotals(
    totalSum: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.title_total_sum),
                style = RsTheme.typography.body,
                color = RsTheme.colors.primaryText.copy(alpha = 0.5f),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = totalSum,
                style = RsTheme.typography.heading,
                color = RsTheme.colors.primaryText,
                textAlign = TextAlign.Center
            )
        }
    }
}
