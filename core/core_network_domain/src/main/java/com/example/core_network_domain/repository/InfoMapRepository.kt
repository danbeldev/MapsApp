package com.example.core_network_domain.repository

import com.example.core_network_domain.entitier.infoMap.InfoMarker
import com.example.core_network_domain.entitier.infoMap.SearchResult

interface InfoMapRepository {

    suspend fun getSearch(
        city:String,
        county:String,
        state:String,
        country:String,
        postalcode:String
    ):List<SearchResult>

    suspend fun getInfoMarker(
        osmIds:String
    ):List<InfoMarker>

    suspend fun getReverse(
        lat:String,
        lon:String
    ):SearchResult
}