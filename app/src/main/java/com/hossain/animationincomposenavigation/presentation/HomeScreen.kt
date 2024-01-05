package com.hossain.animationincomposenavigation.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController
) {
    Box {
        var drawerState by remember {
            mutableStateOf(DrawerState.Closed)
        }
        val coroutineScope = rememberCoroutineScope()

        val drawerWidth = with(LocalDensity.current) {
            DrawerWidth.toPx()
        }
        val translationX = remember {
            Animatable(0f)
        }
        translationX.updateBounds(0f, drawerWidth)

        val draggableState = rememberDraggableState(onDelta = { dragAmount ->
            coroutineScope.launch {
                translationX.snapTo(translationX.value + dragAmount)
            }
        })

        fun toggleDrawerState() {
            coroutineScope.launch {
                if(drawerState == DrawerState.Open){
                    translationX.animateTo(0f)
                }else {
                    translationX.animateTo(drawerWidth)
                }
                drawerState = if (drawerState == DrawerState.Open) {
                    DrawerState.Closed
                } else {
                    DrawerState.Open
                }
            }
        }

        HomeScreenDrawerContents()
        val decay = rememberSplineBasedDecay<Float>()
        ScreenContents(
            onDrawerClicked = ::toggleDrawerState,
            onButtonClicked = {
                navController.navigate("details_screen")
            },
            onFloatingButtonClicked = {
                navController.navigate("floatingA_screen")

            },
            modifier = Modifier
                .graphicsLayer {
                    this.translationX = translationX.value
                    val scale = lerp(1f, 0.8f, translationX.value / drawerWidth)
                    this.scaleX = scale
                    this.scaleY = scale
                    val roundedCorners = lerp(0f, 32.dp.toPx(), translationX.value / drawerWidth)
                    this.shape = RoundedCornerShape(roundedCorners)
                    this.clip = true
                    this.shadowElevation = 32f
                }
                .draggable(draggableState, Orientation.Horizontal,
                    onDragStopped = {velocity ->
                        val decayX = decay.calculateTargetValue(
                            translationX.value,
                            velocity
                        )
                        coroutineScope.launch {
                            val targetX = if(decayX > drawerWidth * 0.5){
                                drawerWidth
                            }else {
                                0f
                            }
                            val canReachTargetWithDecay =
                                (decayX > targetX && targetX == drawerWidth)
                                        || ( decayX < targetX && targetX == 0f)
                            if(canReachTargetWithDecay) {
                                translationX.animateDecay(
                                    initialVelocity = velocity,
                                    animationSpec = decay
                                )
                            }else {
                                translationX.animateTo(
                                    targetX, initialVelocity = velocity
                                )
                            }
                        }
                    }
                )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContents(
    onDrawerClicked: () -> Unit,
    onButtonClicked: () -> Unit,
    onFloatingButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Small Top App Bar")
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            onDrawerClicked()
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    onFloatingButtonClicked()
                }) {
                    Image(
                        imageVector = Icons.Default.Add,
                        contentDescription = "floating button"
                    )
                }
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = {
                    onButtonClicked()
                }) {
                    Text(text = "Go Details Screen")
                }
            }
        }
    }
}


@Composable
private fun HomeScreenDrawerContents(
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        NavigationDrawerItem(
            label = {
                Text("Home")
            },
            icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
            },
            colors =
            NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.White),
            selected = true,
            onClick = {

            },
        )
    }
}

private val DrawerWidth = 250.dp
private enum class DrawerState {
    Open,
    Closed
}