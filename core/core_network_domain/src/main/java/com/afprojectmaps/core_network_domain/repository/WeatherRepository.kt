package com.afprojectmaps.core_network_domain.repository

import com.afprojectmaps.core_network_domain.entities.weather.WeatherAlert
import com.afprojectmaps.core_network_domain.entities.weather.WeatherResult

interface WeatherRepository{

    suspend fun getWeather(
        lat:String,
        lon:String
    ): WeatherResult?

    suspend fun getWeatherAlerts(
        lat:String,
        lon:String
    ):WeatherAlert?

    suspend fun getWeatherDailyHourly(
        lat:String,
        lon:String
    ):WeatherAlert?
}