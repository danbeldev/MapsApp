package com.afprojectmaps.core_network_domain.entities.infoMap

import com.google.gson.annotations.SerializedName

data class Extratag(
    val image:String?,
    @SerializedName("heritage:website")
    val web:String,
    val year_of_construction:String,
    val description:String,
)
