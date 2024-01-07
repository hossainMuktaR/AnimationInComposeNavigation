package com.hossain.animationincomposenavigation.presentation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hossain.animationincomposenavigation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController
) {
    var profileScreen by remember {
        mutableStateOf(false)
    }
    BackHandler {
        backPress(
            profileScreen,
            { profileScreen = false },
            { navController.popBackStack() }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(if (profileScreen) "Profile Screen" else "Details Screen")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        backPress(
                            profileScreen,
                            { profileScreen = false },
                            { navController.popBackStack() }
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Navigation"
                        )
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            val transition =
                updateTransition(targetState = profileScreen, label = "image transition")
            val size by transition.animateDp(
                label = "image size",
                transitionSpec = {
                    tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                }
            ) {
                if (it) 400.dp else 100.dp
            }
            val radius by transition.animateInt(
                label = "image radius",
                transitionSpec = {
                    tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                }
            ) {
                if (it) 0 else 50
            }
            val offsetY by transition.animateDp(
                label = "image offset",
                transitionSpec = {
                    tween(
                        durationMillis = 500,
                        easing = LinearOutSlowInEasing
                    )
                }
            ) {
                if (it) 0.dp else (-250).dp
            }
            Column(modifier = Modifier.padding(if(profileScreen) 0.dp else 16.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.bird_image),
                    contentDescription = "Bird Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(size)
                        .clip(RoundedCornerShape(radius))
                        .clickable {
                            profileScreen = !profileScreen
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
                AnimatedVisibility(
                    visible = !profileScreen
                ) {
                    Text("The Yellow Bird")
                }
            }
        }
    }
}

private fun backPress(state: Boolean, trueCallback: () -> Unit, falseCallback: () -> Unit) {
    if (state) {
        trueCallback()
    } else {
        falseCallback()
    }
}