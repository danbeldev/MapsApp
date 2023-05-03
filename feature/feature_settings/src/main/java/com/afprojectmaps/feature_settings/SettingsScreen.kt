package com.afprojectmaps.feature_settings

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.afprojectmaps.core_database_domain.model.Setting
import com.afprojectmaps.core_database_domain.model.Theme
import com.afprojectmaps.feature_settings.viewModel.SettingViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SettingsScreen(
    navController: NavController,
    settingViewModel: SettingViewModel
) {
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    var settings by remember { mutableStateOf(Setting()) }

    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            settingViewModel.responseSetting.onEach {
                settings = it
            }.collect()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column {
            Text(
                text = "Тематическая карта:",
                modifier = Modifier.padding(5.dp),
                fontWeight = FontWeight.Bold
            )

            LazyRow(content = {
                item {
                    Theme.values().forEach { item ->
                        GoogleMap(
                            modifier = if (item == settings.theme)
                                Modifier
                                    .size(250.dp)
                                    .padding(5.dp)
                                    .clip(AbsoluteRoundedCornerShape(15.dp))
                                    .border(
                                        1.dp,
                                        Color.Black,
                                        AbsoluteRoundedCornerShape(15.dp)
                                    )
                            else Modifier
                                .size(250.dp)
                                .padding(5.dp)
                                .clip(AbsoluteRoundedCornerShape(15.dp)),
                            properties = MapProperties(
                                mapStyleOptions = MapStyleOptions(item.theme)
                            ), uiSettings = MapUiSettings(
                                compassEnabled = false,
                                indoorLevelPickerEnabled = false,
                                mapToolbarEnabled = false,
                                myLocationButtonEnabled = false,
                                rotationGesturesEnabled = false,
                                scrollGesturesEnabled = false,
                                scrollGesturesEnabledDuringRotateOrZoom = false,
                                tiltGesturesEnabled = false,
                                zoomControlsEnabled = false,
                                zoomGesturesEnabled = false
                            ), cameraPositionState = rememberCameraPositionState {
                                position = CameraPosition.fromLatLngZoom(
                                    //Moscow 55.7522, 37.6156
                                    LatLng(55.7522, 37.6156), 13f)
                            }, onMapClick = {
                                settingViewModel.updateSetting(
                                    Setting(
                                        theme = item
                                    )
                                )
                            }
                        )
                    }
                }
            })

            OutlinedButton(
                modifier = Modifier.padding(5.dp),
                onClick = { settingViewModel.deleteHistory() }
            ) {
                Text(text = "Удалить историю")
            }

            OutlinedButton(
                modifier = Modifier.padding(5.dp),
                onClick = { settingViewModel.deleteFavoriteMarkerMap() }
            ) {
                Text(text = "Удалить избранное")
            }
        }
    }
}