package com.example.receiptstorage.presenter.permission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.receiptstorage.presenter.ui.theme.ReceiptStorageTheme
import com.example.receiptstorage.presenter.ui.theme.RsTheme


@Composable
fun RequestPermission(
    modifier: Modifier = Modifier,
    permissionText: String,
    requestButton: () -> Unit,
    cancelButton: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(
                color = RsTheme.colors.secondaryBackground,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            text = permissionText,
            style = RsTheme.typography.body,
            color = RsTheme.colors.secondaryText,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedButton(
                modifier = modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                onClick = cancelButton,
                border = BorderStroke(1.dp, Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
            ) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(android.R.string.cancel),
                    style = RsTheme.typography.body,
                    color = Color.LightGray,
                    textAlign = TextAlign.Center
                )
            }
            Button(
                modifier = modifier.weight(1f),
                shape = RoundedCornerShape(8.dp),
                onClick = requestButton,
                colors = ButtonDefaults.buttonColors(backgroundColor = RsTheme.colors.tintColor)
            ) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = stringResource(com.example.receiptstorage.R.string.title_request_permission),
                    style = RsTheme.typography.body,
                    color = RsTheme.colors.primaryText,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRequestPermissionBottomSheet() {
    ReceiptStorageTheme {
        RequestPermission(permissionText = "Bla bla  bla", requestButton = {}, cancelButton = {})
    }
}

@Preview
@Composable
private fun PreviewRequestPermissionBottomSheetDark() {
    ReceiptStorageTheme(darkTheme = true) {
        RequestPermission(permissionText = "Bla bla  bla", requestButton = {}, cancelButton = {})
    }
}
