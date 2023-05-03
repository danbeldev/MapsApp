package com.afprojectmaps.core_network_domain.entities.route

data class Properties(
    val segments: List<Segment>,
    val summary: Summary,
    val way_points: List<Int>
)