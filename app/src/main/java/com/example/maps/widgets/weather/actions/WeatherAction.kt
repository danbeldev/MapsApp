package com.example.maps.widgets.weather.actions

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.example.maps.di.DaggerAppComponent
import com.example.maps.widgets.weather.WeatherWidget
import com.example.maps.widgets.weather.WeatherWidget.Companion.WEATHER_DATE_KEY
import com.example.maps.widgets.weather.WeatherWidget.Companion.WEATHER_DESCRIPTION_KEY
import com.example.maps.widgets.weather.WeatherWidget.Companion.WEATHER_TEMP_KEY
import com.example.maps.widgets.weather.common.getGPS
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalSerializationApi
class WeatherAction:ActionCallback {

    private val appComponent = DaggerAppComponent
        .builder()
        .build()

    private val getWeatherUseCase = appComponent.getWeatherUseCase()

    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId){
            it.toMutablePreferences()
                .apply {
                    val gps = getGPS(context)
                    val response = getWeatherUseCase.invoke(
                        lat = gps[0],
                        lon = gps[1]
                    )
                    response.data?.let { weather ->
                        this[stringPreferencesKey(WEATHER_TEMP_KEY)] =
                            weather.main.temp.toString()
                        this[stringPreferencesKey(WEATHER_DESCRIPTION_KEY)] =
                            weather.weather[0].description
                        this[stringPreferencesKey(WEATHER_DATE_KEY)] =
                            weather.dt
                    }
                }
        }

        WeatherWidget().update(context,glanceId)
    }
}