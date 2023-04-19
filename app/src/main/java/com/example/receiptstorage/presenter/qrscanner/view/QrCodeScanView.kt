package com.example.receiptstorage.presenter.qrscanner.view

import android.util.Size
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.receiptstorage.presenter.qrscanner.action.QrScannerAction
import com.example.receiptstorage.presenter.qrscanner.analyzer.QrCodeAnalyzer
import com.example.receiptstorage.presenter.qrscanner.viewmodel.QrScannerViewModel
import com.example.receiptstorage.presenter.ui.theme.RsTheme

@Composable
fun QrCodeScanView(
    viewModel: QrScannerViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenSize = LocalConfiguration.current
    val cameraHeight by remember { mutableStateOf((screenSize.screenHeightDp / 3)) }
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var isScan by remember { mutableStateOf(false) }
    Text(
        modifier = modifier
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
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(
                ContextCompat.getMainExecutor(context),
                QrCodeAnalyzer { result ->
                    if (isScan) return@QrCodeAnalyzer

                    isScan = true
                    viewModel.setAction(QrScannerAction.FetchData(result))
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
        modifier = modifier.height(cameraHeight.dp)
    )
}
