package com.example.receiptstorage.presenter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReceiptStorageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        baseDarkPalette
    } else {
        baseLightPalette
    }

    val typography = RsTypography(
        toolbar = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        ),
        heading = TextStyle(
            fontSize = 36.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        body = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
        )
    )

    val shape = RsShape(
        padding = 8.dp,
        shape = RoundedCornerShape(8.dp)
    )

    CompositionLocalProvider(
        LocalRsColors provides colors,
        LocalRsTypography provides typography,
        LocalRsShape provides shape,
        content = content
    )
}
