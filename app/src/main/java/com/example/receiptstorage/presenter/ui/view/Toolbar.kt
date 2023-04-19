package com.example.receiptstorage.presenter.ui.view

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.receiptstorage.presenter.ui.theme.RsTheme

@Composable
fun Toolbar(
    titleText: String,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = titleText,
                style = RsTheme.typography.toolbar,
                color = RsTheme.colors.primaryText,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
        },
        backgroundColor = RsTheme.colors.primaryBackground,
        elevation = 0.dp,
        actions = actions,
        navigationIcon = navigationIcon
    )
}
