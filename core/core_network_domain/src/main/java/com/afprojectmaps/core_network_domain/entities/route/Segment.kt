package com.afprojectmaps.core_network_domain.entities.route

data class Segment(
    val distance: Double,
    val duration: Double,
    val steps: List<Step>
)