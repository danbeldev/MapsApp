package com.afprojectmaps.core_network_domain.entities.route

data class Geometry(
    val coordinates: List<List<Double>>,
    val type: String
)