package com.example.receiptstorage.presenter.util

import androidx.compose.runtime.Composable

@Composable
fun <T> T.remember(): T = androidx.compose.runtime.remember { this }
