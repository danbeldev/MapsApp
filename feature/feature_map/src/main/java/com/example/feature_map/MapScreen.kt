package com.example.feature_map

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.core_utils.style_map.retro
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
fun MapScreen(
    navController: NavController
) {
    var search by remember { mutableStateOf("") }

    val backdropState = rememberBackdropScaffoldState(initialValue = BackdropValue.Revealed)
    val permission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    LaunchedEffect(key1 = Unit, block = {
        permission.launchPermissionRequest()
    })

    BackdropScaffold(
        scaffoldState = backdropState,
        peekHeight = 300.dp,
        backLayerBackgroundColor = Color.Gray,
        appBar = {},
        backLayerContent = {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                properties = MapProperties(
                    isMyLocationEnabled = permission.hasPermission,
                    mapStyleOptions = MapStyleOptions(retro)
                )
            )
        },
        frontLayerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    modifier = Modifier.padding(5.dp),
                    value = search,
                    onValueChange = { search = it },
                    label = { Text(text = "Search") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.Black,
                        disabledTextColor = Color.Black,
                        backgroundColor = Color.White,
                        cursorColor = Color.Black,
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black
                    )
                )
            }
        }
    )
}