package com.example.maps.navigation.navGraph

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.core_utils.navigation.LAT_ARGUMENT
import com.example.core_utils.navigation.LON_ARGUMENT
import com.example.core_utils.navigation.WEATHER_ROUTE
import com.example.core_utils.navigation.WeatherNavScreen
import com.example.feature_weather.WeatherScreen
import com.example.maps.di.AppComponent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
fun NavGraphBuilder.weatherNavGraph(
    navController: NavController,
    appComponent: AppComponent
) {
    navigation(
        route = WEATHER_ROUTE,
        startDestination = WeatherNavScreen.WeatherInfo.route,
        builder = {
            composable(
                route = WeatherNavScreen.WeatherInfo.route,
                arguments = listOf(
                    navArgument(
                        name = LAT_ARGUMENT,
                        builder = {
                            type = NavType.StringType
                            nullable = true
                        }
                    ),
                    navArgument(
                        name = LON_ARGUMENT,
                        builder = {
                            type = NavType.StringType
                            nullable = true
                        }
                    )
                )
            ){
                WeatherScreen(
                    navController = navController,
                    weatherViewModel = appComponent.weatherViewModel(),
                    lat = it.arguments?.getString(LAT_ARGUMENT)?.toDouble(),
                    lon = it.arguments?.getString(LON_ARGUMENT)?.toDouble()
                )
            }
        }
    )
}