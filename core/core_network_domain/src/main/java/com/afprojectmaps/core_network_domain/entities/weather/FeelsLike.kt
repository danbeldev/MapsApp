package com.afprojectmaps.core_network_domain.entities.weather

import kotlinx.serialization.Serializable

@Serializable
data class FeelsLike(
    val day:Float = 0f,
    val night:Float = 0f,
    val eve:Float = 0f,
    val morn:Float = 0f
)
