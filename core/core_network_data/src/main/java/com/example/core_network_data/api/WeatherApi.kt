package com.example.core_network_data.api

import com.example.core_network_data.common.ConstantsUrl.WEATHER_ALERTS
import com.example.core_network_data.common.ConstantsUrl.WEATHER_URL
import com.example.core_network_domain.entities.weather.WeatherAlert
import com.example.core_network_domain.entities.weather.WeatherResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(WEATHER_URL)
    suspend fun getWeather(
        @Query("appid") key:String = "dda74531998c575c691e75f1cd4e6561",
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("units") units:String = "metric",
        @Query("lang") lang:String = "ru"
    ):Response<WeatherResult>

    @GET(WEATHER_ALERTS)
    suspend fun getWeatherAlerts(
        @Query("appid") key:String = "dda74531998c575c691e75f1cd4e6561",
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("units") units:String = "metric",
        @Query("lang") lang:String = "ru",
        @Query("exclude") exclude:String = "alerts"
    ):Response<WeatherAlert>
}