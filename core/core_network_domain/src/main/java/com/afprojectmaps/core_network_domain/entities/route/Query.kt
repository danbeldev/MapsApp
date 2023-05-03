package com.afprojectmaps.core_network_domain.entities.route

data class Query(
    val coordinates: List<List<Double>>,
    val format: String,
    val profile: String
)