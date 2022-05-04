package com.example.maps.navigation.navGraph

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core_utils.navigation.MAP_ROUE
import com.example.core_utils.navigation.MapNavScreen
import com.example.feature_map.MapScreen
import com.example.feature_settings.SettingsScreen
import com.example.maps.di.AppComponent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
fun NavGraphBuilder.mapNavGraph(
    navController: NavController,
    appComponent: AppComponent
) {
    navigation(
        startDestination = MapNavScreen.Map.route,
        route = MAP_ROUE,
        builder = {
            composable(MapNavScreen.Map.route){
                MapScreen(
                    navController = navController,
                    mapViewModel = appComponent.mapViewModel()
                )
            }
            composable(MapNavScreen.Setting.route){
                SettingsScreen(
                    navController = navController,
                    settingViewModel = appComponent.settingViewModel()
                )
            }
        }
    )
}