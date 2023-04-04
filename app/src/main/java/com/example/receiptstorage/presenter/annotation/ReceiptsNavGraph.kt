package com.example.receiptstorage.presenter.annotation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class ReceiptsNavGraph(val start: Boolean = false)
