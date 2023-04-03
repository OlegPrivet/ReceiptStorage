package com.example.receiptstorage.annotation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class ReceiptsNavGraph(val start: Boolean = false)
