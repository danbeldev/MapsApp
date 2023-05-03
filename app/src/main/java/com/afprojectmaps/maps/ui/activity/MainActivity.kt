package com.afprojectmaps.maps.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.afprojectmaps.maps.di.DaggerAppComponent
import com.afprojectmaps.maps.navigation.host.BaseNavHost
import com.afprojectmaps.maps.ui.theme.MapsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalFoundationApi
@ExperimentalSerializationApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()

        setContent {
            val navHostController = rememberNavController()

            MapsTheme(
                darkTheme = false
            ) {
                BaseNavHost(
                    navHostController = navHostController,
                    appComponent = appComponent
                )
            }
        }
    }
}