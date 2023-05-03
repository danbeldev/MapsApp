package com.afprojectmaps.core_database_domain.model

data class History(
    val id:Int,
    val lat:Double,
    val lon:Double,
    val name:String,
    val transport:String
)