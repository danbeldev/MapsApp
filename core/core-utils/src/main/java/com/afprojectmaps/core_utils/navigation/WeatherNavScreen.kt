package com.afprojectmaps.core_utils.navigation

const val WEATHER_ROUTE = "weather_route"

const val LAT_ARGUMENT = "lat"
const val LON_ARGUMENT = "lon"

sealed class WeatherNavScreen(val route:String) {
    object WeatherInfo:WeatherNavScreen("weather_info_screen?lat={lat}&lon={lon}"){
        fun data(
            lat:String,
            lon:String
        ):String = "weather_info_screen?lat=$lat&lon=$lon"
    }
}