package com.example.receiptstorage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.navigation.plusAssign
import com.example.receiptstorage.ui.theme.ReceiptStorageTheme
import com.example.receiptstorage.ui.theme.baseDarkPalette
import com.example.receiptstorage.ui.theme.baseLightPalette
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
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
                val navController = rememberAnimatedNavController()
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                navController.navigatorProvider += bottomSheetNavigator
                val navHostEngine = rememberAnimatedNavHostEngine(
                    navHostContentAlignment = Alignment.TopCenter,
                    rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
                    defaultAnimationsForNestedNavGraph = mapOf(
                        NavGraphs.receipts to NestedNavGraphDefaultAnimations(
                            enterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentScope.SlideDirection.Left,
                                    animationSpec = tween(TRANSITION_ANIMATION_DURATION_MILLISECONDS)
                                )
                            },
                            popEnterTransition = {
                                slideIntoContainer(
                                    towards = AnimatedContentScope.SlideDirection.Right,
                                    animationSpec = tween(TRANSITION_ANIMATION_DURATION_MILLISECONDS)
                                )
                            },
                        ),
                    )
                )
                ModalBottomSheetLayout(
                    bottomSheetNavigator = bottomSheetNavigator,
                    sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                    sheetElevation = 16.dp
                ) {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        navController = navController,
                        engine = navHostEngine
                    )
                }
            }
        }
    }

    private companion object {
        const val TRANSITION_ANIMATION_DURATION_MILLISECONDS = 400
    }
}
