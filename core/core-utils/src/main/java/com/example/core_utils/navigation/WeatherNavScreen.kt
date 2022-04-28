package com.example.core_utils.navigation

const val WEATHER_ROUTE = "weather_route"

sealed class WeatherNavScreen(val route:String) {
    object WeatherInfo:WeatherNavScreen("weather_info_screen")
}