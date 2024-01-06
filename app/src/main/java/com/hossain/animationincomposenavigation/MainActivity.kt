package com.hossain.animationincomposenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hossain.animationincomposenavigation.presentation.DetailsScreen
import com.hossain.animationincomposenavigation.presentation.FloatingActionScreen
import com.hossain.animationincomposenavigation.presentation.HomeScreen
import com.hossain.animationincomposenavigation.ui.theme.AnimationInComposeNavigationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationInComposeNavigationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home_screen",
                        enterTransition = { EnterTransition.None},
                        exitTransition = { ExitTransition.None}
                    ) {
                        composable(
                            route = "home_screen"
                        ) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = "details_screen",
                            enterTransition = {
                                fadeIn(
                                    animationSpec = tween(
                                        500, easing = LinearEasing
                                    )
                                ) + slideIntoContainer(
                                    animationSpec = tween(500, easing =  LinearEasing),
                                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                                )
                            },
                            exitTransition = {
                                fadeOut(
                                    animationSpec = tween(
                                        500, easing = LinearEasing
                                    )
                                ) + slideOutOfContainer(
                                    animationSpec = tween(500, easing = EaseOut),
                                    towards = AnimatedContentTransitionScope.SlideDirection.End
                                )
                            }
                        ) {
                            DetailsScreen(navController)
                        }
                        composable(
                            route = "floatingA_screen"
                        ) {
                            FloatingActionScreen(navController)
                        }
                    }
                }
            }
        }
    }
}