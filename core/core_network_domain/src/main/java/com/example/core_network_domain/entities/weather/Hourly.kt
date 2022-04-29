package com.example.core_network_domain.entities.weather

import com.example.core_network_domain.serialization.DateTimeSerialization
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class Hourly(
    @Serializable(with = DateTimeSerialization::class)
    val dt:String,
    val temp:Float,
    val feels_like:Float,
    val pressure:Int,
    val humidity:Int,
    val dew_point:Float,
    val uvi:Float,
    val clouds:Int,
    val visibility:Int,
    val wind_speed:Float,
    val wind_deg:Int,
    val wind_gust:Float,
    val weather: List<Weather>,
    val pop:Float
)