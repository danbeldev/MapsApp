package com.example.maps.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core_utils.navigation.WEATHER_ROUTE
import com.example.core_utils.navigation.WeatherNavScreen
import com.example.feature_weather.WeatherScreen
import com.example.maps.di.AppComponent
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
fun NavGraphBuilder.weatherNavGraph(
    navController: NavController,
    appComponent: AppComponent
) {
    navigation(
        route = WEATHER_ROUTE,
        startDestination = WeatherNavScreen.WeatherInfo.route,
        builder = {
            composable(WeatherNavScreen.WeatherInfo.route){
                WeatherScreen(
                    navController = navController,
                    weatherViewModel = appComponent.weatherViewModel()
                )
            }
        }
    )
}