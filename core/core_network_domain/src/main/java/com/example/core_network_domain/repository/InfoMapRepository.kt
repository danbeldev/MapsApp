package com.example.core_network_domain.repository

import com.example.core_network_domain.entities.infoMap.InfoMarker
import com.example.core_network_domain.entities.infoMap.SearchResult

interface InfoMapRepository {

    suspend fun getSearch(
        city:String,
        county:String,
        country:String,
        postalcode:String,
        street:String
    ):List<SearchResult>

    suspend fun getInfoMarker(
        osmIds:String
    ):List<InfoMarker>

    suspend fun getReverse(
        lat:String,
        lon:String
    ):SearchResult
}