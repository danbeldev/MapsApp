package com.example.core_network_domain.entities.weather

import com.example.core_network_domain.serialization.DateWeekSerialization
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@ExperimentalSerializationApi
@Serializable
data class Daily(
    @Serializable(with = DateWeekSerialization::class)
    val dt:String,
    val weather: List<Weather> = listOf(),
    val feels_like:FeelsLike = FeelsLike(),
    val temp:Temp = Temp()
)
