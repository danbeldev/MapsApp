package com.example.feature_intro_start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.example.core_utils.navigation.MapNavScreen
import kotlinx.coroutines.delay

@Composable
fun IntroStartScreen(
    navController: NavController
) {
    val animationMap = rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.map))

    val progressAnimationMap = animateLottieCompositionAsState(
        composition = animationMap.value,
        iterations = LottieConstants.IterateForever
    )

    LaunchedEffect(key1 = Unit, block = {
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