package com.example.core_network_domain.entities.weather

data class Daily(
    val weather: List<Weather> = listOf(),
    val feels_like:FeelsLike = FeelsLike(),
    val temp:Temp = Temp()
)
