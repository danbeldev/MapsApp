package com.afprojectmaps.core_network_domain.entities.weather

import com.afprojectmaps.core_network_domain.serialization.DateTimeSerialization
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class Sys(
    val country: String,
    @Serializable(with = DateTimeSerialization::class)
    val sunrise: String,
    @Serializable(with = DateTimeSerialization::class)
    val sunset: String
)