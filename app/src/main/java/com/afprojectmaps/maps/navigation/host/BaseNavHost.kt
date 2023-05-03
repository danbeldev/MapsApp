package com.afprojectmaps.maps.navigation.host

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.afnotesproject.notes.screens.withdrawalRequestsScreen.WithdrawalRequestsScreen
import com.afprojectmaps.core_utils.navigation.START_ROUTE
import com.afprojectmaps.maps.di.AppComponent
import com.afprojectmaps.maps.navigation.navGraph.mapNavGraph
import com.afprojectmaps.maps.navigation.navGraph.startNavGraph
import com.afprojectmaps.maps.navigation.navGraph.weatherNavGraph
import com.afprojectmaps.maps.screens.adminScreen.AdminScreen
import com.afprojectmaps.maps.screens.adsScreen.AdsScreen
import com.afprojectmaps.maps.screens.authScreen.AuthScreen
import com.afprojectmaps.maps.screens.settingsScreen.SettingsScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@ExperimentalFoundationApi
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
                appComponent = appComponent,
                onAdsScreen = {
                    val auth = FirebaseAuth.getInstance()

                    if(auth.currentUser == null){
                       navHostController.navigate("AuthScreen")
                    }else{
                        navHostController.navigate("AdsScreen")
                    }
                }
            )

            weatherNavGraph(
                navController = navHostController,
                appComponent = appComponent
            )

            composable("AdsScreen"){
                AdsScreen(navHostController)
            }

            composable("AuthScreen"){
                AuthScreen(navHostController)
            }

            composable("AdminScreen"){
                AdminScreen(navController = navHostController)
            }

            composable("SettingsScreen"){
                SettingsScreen(navController = navHostController)
            }

            composable(
                "WithdrawalRequestsScreen/{status}",
                arguments = listOf(
                    navArgument("status"){
                        type = NavType.StringType
                    }
                )
            ) {
                WithdrawalRequestsScreen(
                    navController = navHostController,
                    withdrawalRequestStatus = enumValueOf(
                        it.arguments!!.getString("status").toString()
                    )
                )
            }
        }
    )
}