package com.example.receiptstorage.presenter.qrscanner

import android.Manifest
import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.receiptstorage.R
import com.example.receiptstorage.core.connection.ConnectionState
import com.example.receiptstorage.presenter.annotation.ReceiptsNavGraph
import com.example.receiptstorage.presenter.connection.connectivityState
import com.example.receiptstorage.presenter.destinations.ReceiptScreenDestination
import com.example.receiptstorage.presenter.qrscanner.action.QrScannerAction
import com.example.receiptstorage.presenter.qrscanner.state.QrScannerScreenState
import com.example.receiptstorage.presenter.qrscanner.view.QrCodeFetchDataView
import com.example.receiptstorage.presenter.qrscanner.view.QrCodeScanView
import com.example.receiptstorage.presenter.qrscanner.viewmodel.QrScannerViewModel
import com.example.receiptstorage.presenter.ui.theme.RsTheme
import com.example.receiptstorage.presenter.ui.view.BottomSheetDialogError
import com.example.receiptstorage.presenter.util.remember
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@OptIn(ExperimentalPermissionsApi::class)
@ReceiptsNavGraph
@Destination(style = DestinationStyle.BottomSheet::class)
@Composable
fun QrScannerScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: QrScannerViewModel = hiltViewModel(),
) {
    val connection by connectivityState()

    val isConnected by remember {
        derivedStateOf { connection === ConnectionState.Available }
    }

    if (isConnected) {
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()
        val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
        if (permissionState.status.isGranted) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = RsTheme.colors.secondaryBackground),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                when (uiState.value) {
                    is QrScannerScreenState.Scanning -> QrCodeScanView(viewModel)
                    is QrScannerScreenState.FetchingData -> QrCodeFetchDataView()
                    is QrScannerScreenState.ShowingResult.ShowError -> BottomSheetDialogError(
                        errorText = (uiState.value as QrScannerScreenState.ShowingResult.ShowError).error,
                        okButton = remember {
                            {
                                viewModel.setAction(QrScannerAction.ScanQr)
                            }
                        },
                        okButtonText = stringResource(id = R.string.repeat_request),
                        cancelButton = remember {
                            {
                                destinationsNavigator.popBackStack()
                            }
                        }
                    )

                    is QrScannerScreenState.ShowingResult.ShowSuccess -> {
                        destinationsNavigator.navigate(
                            ReceiptScreenDestination(
                                (uiState.value as QrScannerScreenState.ShowingResult.ShowSuccess).receiptId
                            )
                        )
                    }
                }
            }
        } else {
            BottomSheetDialogError(
                errorText = stringResource(R.string.title_camera_rational),
                okButton = { permissionState.launchPermissionRequest() }.remember(),
                okButtonText = stringResource(R.string.title_request_permission),
                cancelButton = remember { { destinationsNavigator.popBackStack() } }
            )
        }
    } else {
        ConnectionErrorView(destinationsNavigator)
    }
}

@Composable
fun ConnectionErrorView(destinationsNavigator: DestinationsNavigator) {
    val context = LocalContext.current
    BottomSheetDialogError(
        errorText = stringResource(R.string.connection_error),
        okButton = remember {
            {
                val intent = Intent(Settings.ACTION_SETTINGS)
                context.startActivity(intent)
                destinationsNavigator.popBackStack()
            }
        },
        okButtonText = stringResource(R.string.go_to_settings),
        cancelButton = remember { { destinationsNavigator.popBackStack() } }
    )
}
