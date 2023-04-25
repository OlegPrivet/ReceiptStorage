package com.example.receiptstorage.presenter.qrscanner.view

import android.util.Size
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.view.PreviewView.ImplementationMode.COMPATIBLE
import androidx.compose.foundation.layout.Box
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
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import timber.log.Timber

@Composable
fun QrCodeScanView(
    viewModel: QrScannerViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenSize = LocalConfiguration.current
    val cameraHeight by remember { mutableStateOf((screenSize.screenHeightDp / 3)) }
    var preview by remember { mutableStateOf<Preview?>(null) }
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
            PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(cameraHeight, cameraHeight)
                scaleType = PreviewView.ScaleType.FILL_CENTER
                implementationMode = COMPATIBLE
            }
        },
        update = { previewView ->
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = QrCodeAnalyzer { barcodes ->
                    barcodes.forEach { barcode ->
                        val barcodeValue = barcode.rawValue ?: return@forEach
                        viewModel.setAction(QrScannerAction.FetchData(barcodeValue))
                    }
                }
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Timber.d("CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        },
        modifier = modifier.height(cameraHeight.dp)
    )
}
