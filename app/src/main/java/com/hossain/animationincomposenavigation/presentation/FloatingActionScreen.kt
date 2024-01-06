package com.hossain.animationincomposenavigation.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingActionScreen(
    navController: NavController
) {
    var fabState by remember {
        mutableStateOf(FabState.Explode)
    }
    val fabColor = MaterialTheme.colorScheme.primary
    val animDuration = 500

    val fabSize by animateFloatAsState(
        targetValue = if(fabState == FabState.Explode) 4000f else 80f,
        animationSpec = tween(
            durationMillis = animDuration
        ),
        label = "fab size",
        finishedListener = {
            navController.popBackStack()
        }
    )
    val fabAlpha by animateFloatAsState(
        targetValue = if(fabState == FabState.Explode) 1f else 0.5f,
        animationSpec = tween(
            durationMillis = animDuration
        ),
        label = "fab alpha")

    BackHandler {
        fabState = FabState.Idle
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp), contentAlignment = Alignment.BottomEnd
    ) {
        Canvas(modifier = Modifier.padding(16.dp)) {
            drawCircle(
                fabColor, fabSize, alpha = fabAlpha
            )
        }
    }
    AnimatedVisibility(
        visible = fabState == FabState.Explode,
        enter = fadeIn(
            animationSpec = tween(
                5000, easing = LinearOutSlowInEasing
            )
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                    title = {
                        Text("FloatingA Screen")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            fabState = FabState.Idle
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back Navigation",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "FloatingA Screen", style = TextStyle(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}
