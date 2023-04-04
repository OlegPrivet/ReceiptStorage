package com.example.receiptstorage.presenter.ui.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.receiptstorage.presenter.ui.theme.RsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReceiptBackdropScaffold(
    appBar: @Composable () -> Unit,
    backLayerContent: @Composable () -> Unit,
    frontLayerContent: @Composable () -> Unit,
) {
    BackdropScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = rememberBackdropScaffoldState(BackdropValue.Revealed),
        frontLayerShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        appBar = appBar,
        backLayerContent = backLayerContent,
        backLayerBackgroundColor = RsTheme.colors.primaryBackground,
        frontLayerContent = frontLayerContent,
        frontLayerBackgroundColor = RsTheme.colors.secondaryBackground,
        frontLayerScrimColor = Color.Unspecified,
    )
}
