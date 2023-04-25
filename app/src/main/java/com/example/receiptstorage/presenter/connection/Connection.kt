package com.example.receiptstorage.presenter.connection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.example.receiptstorage.core.connection.ConnectionState
import com.example.receiptstorage.core.connection.currentConnectivityState
import com.example.receiptstorage.core.connection.observeConnectivityAsFlow

@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityState) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}
