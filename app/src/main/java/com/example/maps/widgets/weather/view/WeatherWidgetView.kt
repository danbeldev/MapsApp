package com.example.maps.widgets.weather.view

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.*
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.layout.*
import androidx.glance.text.Text
import com.example.maps.MainActivity
import com.example.maps.R
import com.example.maps.widgets.weather.WeatherWidget.Companion.WEATHER_DATE_KEY
import com.example.maps.widgets.weather.WeatherWidget.Companion.WEATHER_DESCRIPTION_KEY
import com.example.maps.widgets.weather.WeatherWidget.Companion.WEATHER_TEMP_KEY
import com.example.maps.widgets.weather.actions.WeatherAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalFoundationApi
@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@ExperimentalSerializationApi
@Composable
fun WeatherWidgetView() {

    val context = LocalContext.current
    val prefs = currentState<Preferences>()
    val temp = prefs[stringPreferencesKey(WEATHER_TEMP_KEY)] ?: ""
    val description = prefs[stringPreferencesKey(WEATHER_DESCRIPTION_KEY)] ?: ""
    val date = prefs[stringPreferencesKey(WEATHER_DATE_KEY)] ?: ""

    Column(
        modifier = GlanceModifier
            .background(Color(0xFF23282D))
            .clickable(
                onClick = actionStartActivity(Intent(context, MainActivity::class.java))
            ),
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        Text(
            text = "$temp °",
            modifier = GlanceModifier
                .padding(5.dp)
                .defaultWeight()
        )

        Text(
            text = description,
            modifier = GlanceModifier
                .padding(5.dp)
                .defaultWeight()
        )

        Text(
            text = date,
            modifier = GlanceModifier
                .padding(5.dp)
                .defaultWeight()
        )

        Row(
            horizontalAlignment = Alignment.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                provider = ImageProvider(resId = R.drawable.reboot),
                contentDescription = null,
                modifier = GlanceModifier
                    .padding(5.dp)
                    .size(40.dp)
                    .clickable(
                        onClick = actionRunCallback<WeatherAction>()
                    )
                    .defaultWeight()
            )
        }
    }
}