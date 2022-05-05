package com.example.maps.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.example.maps.di.DaggerAppComponent
import com.example.maps.navigation.host.BaseNavHost
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
            BaseNavHost(
                navHostController = navHostController,
                appComponent = appComponent
            )
        }
    }
}