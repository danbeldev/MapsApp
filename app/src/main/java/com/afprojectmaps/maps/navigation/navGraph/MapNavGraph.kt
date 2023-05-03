package com.afprojectmaps.maps.navigation.navGraph

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.afprojectmaps.core_utils.navigation.MAP_ROUE
import com.afprojectmaps.core_utils.navigation.MapNavScreen
import com.afprojectmaps.feature_map.MapScreen
import com.afprojectmaps.feature_settings.SettingsScreen
import com.afprojectmaps.maps.di.AppComponent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
fun NavGraphBuilder.mapNavGraph(
    navController: NavController,
    appComponent: AppComponent,
    onAdsScreen: () -> Unit
) {
    navigation(
        startDestination = MapNavScreen.Map.route,
        route = MAP_ROUE,
        builder = {
            composable(MapNavScreen.Map.route){
                MapScreen(
                    navController = navController,
                    mapViewModel = appComponent.mapViewModel(),
                    onAdsScreen = onAdsScreen
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