package com.example.receiptstorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.receiptstorage.ui.theme.ReceiptStorageTheme
import com.example.receiptstorage.ui.theme.RsTheme
import com.example.receiptstorage.ui.theme.baseDarkPalette
import com.example.receiptstorage.ui.theme.baseLightPalette
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkMode = isSystemInDarkTheme()
            ReceiptStorageTheme(
                darkTheme = darkMode
            ) {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = if (darkMode) baseDarkPalette.primaryBackground else baseLightPalette.primaryBackground,
                    )
                    systemUiController.setNavigationBarColor(
                        if (darkMode) baseDarkPalette.secondaryBackground else baseLightPalette.secondaryBackground,
                    )
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = RsTheme.colors.primaryBackground
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", color = RsTheme.colors.primaryText)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ReceiptStorageTheme {
        Greeting("Android")
    }
}
