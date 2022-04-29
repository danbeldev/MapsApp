package com.example.core_network_domain.entities.weather

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class WeatherAlert(
    val daily:List<Daily> = listOf(),
    val hourly:List<Hourly> = listOf()
)