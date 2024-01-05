package com.hossain.animationincomposenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                    NavHost(navController = navController, startDestination = "home_screen") {
                        composable(route = "home_screen") {
                            HomeScreen(navController)
                        }
                        composable(route = "details_screen") {
                            DetailsScreen(navController)
                        }
                        composable(route = "floatingA_screen") {
                            FloatingActionScreen(navController)
                        }
                    }
                }
            }
        }
    }
}