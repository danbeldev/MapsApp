package com.example.core_network_domain.entities.weather

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val description: String = "",
    val icon: String = "",
    val id: Int = 0,
    val main: String = ""
)