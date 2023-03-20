package com.example.receiptstorage.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class RsColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
)

data class RsTypography(
    val toolbar: TextStyle,
    val heading: TextStyle,
    val body: TextStyle,
)

data class RsShape(
    val padding: Dp,
    val shape: Shape,
)

object RsTheme {

    val colors: RsColors
        @Composable
        get() = LocalRsColors.current

    val typography: RsTypography
        @Composable
        get() = LocalRsTypography.current

    val shape: RsShape
        @Composable
        get() = LocalRsShape.current
}

val LocalRsColors = staticCompositionLocalOf<RsColors> { error("No colors provide") }
val LocalRsTypography = staticCompositionLocalOf<RsTypography> { error("No typography provide") }
val LocalRsShape = staticCompositionLocalOf<RsShape> { error("No shape provide") }
