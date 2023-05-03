package com.afprojectmaps.core_network_domain.entities.route

data class Route(
    val bbox: List<Double>,
    val features: List<Feature>,
    val metadata: Metadata,
    val type: String
)