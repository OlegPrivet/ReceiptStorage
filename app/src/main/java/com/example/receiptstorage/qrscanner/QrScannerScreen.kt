package com.example.receiptstorage.qrscanner

import android.Manifest
import android.util.Size
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.receiptstorage.R
import com.example.receiptstorage.annotation.ReceiptsNavGraph
import com.example.receiptstorage.permission.RequestPermission
import com.example.receiptstorage.qrscanner.analyzer.QrCodeAnalyzer
import com.example.receiptstorage.ui.theme.RsTheme
import com.example.receiptstorage.util.remember
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
) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    if (permissionState.status.isGranted) {
        QRCode(destinationsNavigator = destinationsNavigator)
    } else {
        RequestPermission(
            permissionText = stringResource(R.string.title_camera_rational),
            requestButton = { permissionState.launchPermissionRequest() }.remember(),
            cancelButton = remember { { destinationsNavigator.popBackStack() } }
        )
    }
}

@Composable
private fun QRCode(
    destinationsNavigator: DestinationsNavigator,
) {
    var code by remember { mutableStateOf("") }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenSize = LocalConfiguration.current
    val cameraHeight by remember { mutableStateOf((screenSize.screenHeightDp / 3)) }
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = RsTheme.colors.secondaryBackground),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            text = stringResource(R.string.title_qr_code),
            style = RsTheme.typography.heading,
            color = RsTheme.colors.secondaryText,
            textAlign = TextAlign.Center
        )
        AndroidView(
            factory = { context ->
                val previewView = PreviewView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(cameraHeight, cameraHeight)
                }
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                preview.setSurfaceProvider(previewView.surfaceProvider)
                val imageAnalysis = ImageAnalysis.Builder()
                    .setTargetResolution(Size(cameraHeight, cameraHeight))
                    .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                imageAnalysis.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QrCodeAnalyzer { result ->
                        code = result
                    }
                )
                try {
                    cameraProviderFuture.get().bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                previewView
            },
            modifier = Modifier.height(cameraHeight.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = code,
            style = RsTheme.typography.body,
            color = RsTheme.colors.secondaryText,
            textAlign = TextAlign.Center
        )
    }
}
