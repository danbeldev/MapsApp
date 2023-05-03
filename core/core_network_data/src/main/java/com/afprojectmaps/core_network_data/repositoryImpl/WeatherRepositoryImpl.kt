package com.afprojectmaps.core_network_data.repositoryImpl

import com.afprojectmaps.core_network_data.api.WeatherApi
import com.afprojectmaps.core_network_domain.entities.weather.WeatherAlert
import com.afprojectmaps.core_network_domain.entities.weather.WeatherResult
import com.afprojectmaps.core_network_domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
): WeatherRepository {
    override suspend fun getWeather(lat: String, lon: String): WeatherResult? {
        return weatherApi.getWeather(lat = lat, lon = lon).body()
    }

    override suspend fun getWeatherAlerts(lat: String, lon: String): WeatherAlert? {
        return weatherApi.getWeather(lat = lat, lon = lon, exclude = "alerts").body()
    }

    override suspend fun getWeatherDailyHourly(lat: String, lon: String): WeatherAlert? {
        return weatherApi.getWeather(lat = lat, lon = lon, exclude = "alerts").body()
    }
}