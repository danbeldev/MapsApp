package com.afprojectmaps.maps.widgets.weather.actions

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.afprojectmaps.maps.di.DaggerAppComponent
import com.afprojectmaps.maps.widgets.weather.WeatherWidget
import com.afprojectmaps.maps.widgets.weather.WeatherWidget.Companion.WEATHER_DATE_KEY
import com.afprojectmaps.maps.widgets.weather.WeatherWidget.Companion.WEATHER_DESCRIPTION_KEY
import com.afprojectmaps.maps.widgets.weather.WeatherWidget.Companion.WEATHER_TEMP_KEY
import com.afprojectmaps.maps.widgets.weather.common.getGPS
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalSerializationApi
class WeatherAction:ActionCallback {

    override suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val appComponent = DaggerAppComponent
            .builder()
            .context(context)
            .build()
        val getWeatherUseCase = appComponent.getWeatherUseCase()
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