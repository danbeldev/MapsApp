package com.afprojectmaps.maps.widgets.weather

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalSerializationApi
class WeatherWidgetReceiver:GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = WeatherWidget()
}