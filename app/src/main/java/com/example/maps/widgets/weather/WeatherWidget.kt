package com.example.maps.widgets.weather

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.example.maps.widgets.weather.view.WeatherWidgetView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@ExperimentalSerializationApi
class WeatherWidget:GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        WeatherWidgetView()
    }

    companion object{
        const val WEATHER_TEMP_KEY = "weather_temp_key"
        const val WEATHER_DESCRIPTION_KEY = "weather_description_key"
        const val WEATHER_DATE_KEY = "weather_date_key"
    }
}