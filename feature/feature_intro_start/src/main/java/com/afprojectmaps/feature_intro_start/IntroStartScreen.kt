package com.afprojectmaps.feature_intro_start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.afprojectmaps.core_utils.navigation.MapNavScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun IntroStartScreen(
    navController: NavController
) {
    val useDarkIcons = MaterialTheme.colors.isLight
    val systemUiController = rememberSystemUiController()
    val animationMap = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.map))

    val progressAnimationMap = animateLottieCompositionAsState(
        composition = animationMap.value,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(key1 = Unit, block = {
        systemUiController.setStatusBarColor(
            color = Color.White,
            darkIcons = useDarkIcons
        )
        systemUiController.setNavigationBarColor(
            color = Color.White,
            darkIcons = true
        )
        delay(3500L)
        navController.navigate(MapNavScreen.Map.route)
    })

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LottieAnimation(
                modifier = Modifier.fillMaxSize(),
                composition = animationMap.value,
                progress = progressAnimationMap.progress
            )
        }
    }
}