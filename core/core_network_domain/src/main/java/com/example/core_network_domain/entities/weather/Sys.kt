package com.example.core_network_domain.entities.weather

import com.example.core_network_domain.serialization.DateTimeSerialization
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class Sys(
    val country: String,
    val id: Int?,
    @Serializable(with = DateTimeSerialization::class)
    val sunrise: String,
    @Serializable(with = DateTimeSerialization::class)
    val sunset: String,
    val type: Int?
)