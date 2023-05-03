package com.afprojectmaps.core_network_domain.entities.weather

import com.afprojectmaps.core_network_domain.serialization.DateTimeSerialization
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class WeatherResult(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    @Serializable(with = DateTimeSerialization::class)
    val dt: String,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys?,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)