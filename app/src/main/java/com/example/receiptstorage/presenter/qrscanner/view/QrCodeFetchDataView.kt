package com.example.receiptstorage.presenter.qrscanner.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.receiptstorage.R
import com.example.receiptstorage.presenter.ui.theme.RsTheme

@Composable
fun QrCodeFetchDataView(
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        text = stringResource(R.string.fetching_data_qr_code),
        style = RsTheme.typography.heading,
        color = RsTheme.colors.secondaryText,
        textAlign = TextAlign.Center
    )
    CircularProgressIndicator(
        color = RsTheme.colors.tintColor,
    )
}
