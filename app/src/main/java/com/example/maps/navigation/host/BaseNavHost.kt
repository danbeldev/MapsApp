package com.example.maps.navigation.host

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core_utils.navigation.START_ROUTE
import com.example.maps.di.AppComponent
import com.example.maps.navigation.navGraph.mapNavGraph
import com.example.maps.navigation.navGraph.startNavGraph
import com.example.maps.navigation.navGraph.weatherNavGraph
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun BaseNavHost(
    navHostController: NavHostController,
    appComponent:AppComponent
) {
    NavHost(
        navController = navHostController,
        startDestination = START_ROUTE,
        route = "route",
        builder = {
            startNavGraph(
                navController = navHostController
            )
            mapNavGraph(
                navController = navHostController,
                appComponent = appComponent
            )

            weatherNavGraph(
                navController = navHostController,
                appComponent = appComponent
            )
        }
    )
}