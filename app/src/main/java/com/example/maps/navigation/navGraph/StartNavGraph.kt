package com.example.maps.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core_utils.navigation.START_ROUTE
import com.example.core_utils.navigation.StartNavScreen
import com.example.feature_intro_start.IntroStartScreen

fun NavGraphBuilder.startNavGraph(
    navController: NavController
) {
    navigation(
        startDestination = StartNavScreen.StartInfo.route,
        route = START_ROUTE,
        builder = {
            composable(
                route = StartNavScreen.StartInfo.route,
                content = {
                    IntroStartScreen(
                        navController = navController
                    )
                }
            )
        }
    )
}