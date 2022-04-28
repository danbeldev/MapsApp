package com.example.core_network_domain.entities.weather

data class WeatherAlert(
    val daily:List<Daily> = listOf(),
    val hourly:List<Hourly> = listOf()
)