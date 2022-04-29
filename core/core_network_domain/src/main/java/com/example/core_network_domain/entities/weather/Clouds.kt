package com.example.core_network_domain.entities.weather

import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
    val all: Int
)