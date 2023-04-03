package com.example.receiptstorage.listscreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.receiptstorage.R
import com.example.receiptstorage.annotation.ReceiptsNavGraph
import com.example.receiptstorage.destinations.QrScannerScreenDestination
import com.example.receiptstorage.destinations.ReceiptScreenDestination
import com.example.receiptstorage.listscreen.view.ReceiptList
import com.example.receiptstorage.listscreen.view.ReceiptTotals
import com.example.receiptstorage.ui.theme.RsTheme
import com.example.receiptstorage.ui.view.ReceiptBackdropScaffold
import com.example.receiptstorage.ui.view.Toolbar
import com.example.receiptstorage.util.remember
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterialApi::class)
@ReceiptsNavGraph(start = true)
@Destination
@Composable
fun ReceiptListScreen(
    destinationsNavigator: DestinationsNavigator,
) {
    ReceiptBackdropScaffold(
        appBar = remember {
            {
                Toolbar(
                    titleText = stringResource(id = R.string.app_name),
                    actions = {
                        IconButton(
                            onClick = { destinationsNavigator.navigate(QrScannerScreenDestination) }.remember(),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add",
                                tint = RsTheme.colors.primaryText
                            )
                        }
                    }
                )
            }
        },
        backLayerContent = remember {
            {
                ReceiptTotals(totalSum = "$ 23 093.20")
            }
        },
        frontLayerContent = remember {
            {
                ReceiptList(
                    modifier = Modifier,
                    dates = listOf(
                        "All",
                        "03.23",
                        "04.23",
                        "05.23",
                        "06.23",
                        "07.23",
                        "08.23",
                        "09.23",
                        "10.23"
                    ).toImmutableList(),
                    receipts = listOf(
                        "All",
                        "03.23",
                        "04.23",
                        "05.23",
                        "06.23",
                        "07.23",
                        "08.23",
                        "09.23",
                        "10.23",
                        "All1",
                        "03.231",
                        "04.231",
                        "05.231",
                        "06.231",
                        "07.231",
                        "08.231",
                        "09.231",
                        "10.231"
                    ).toImmutableList(),
                    dateOnClick = remember { {} },
                    receiptOnClick = remember { { destinationsNavigator.navigate(ReceiptScreenDestination)} },
                )
            }
        },
    )
}
