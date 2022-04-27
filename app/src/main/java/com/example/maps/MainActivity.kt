package com.example.maps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.compose.rememberNavController
import com.example.maps.navigation.host.BaseNavHost
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            BaseNavHost(
                navHostController = navHostController
            )
        }
    }
}