package com.example.core_network_data.repositoryImpl

import com.example.core_network_data.api.InfoMapApi
import com.example.core_network_domain.entitier.infoMap.InfoMarker
import com.example.core_network_domain.entitier.infoMap.SearchResult
import com.example.core_network_domain.repository.InfoMapRepository
import javax.inject.Inject

class InfoMapRepositoryImpl @Inject constructor (
    private val infoMapApi: InfoMapApi
):InfoMapRepository {
    override suspend fun getSearch(
        city: String,
        county: String,
        state: String,
        country: String,
        postalcode: String
    ): List<SearchResult> {
        val response = infoMapApi.getSearch(
            city = city,
            country = country,
            state = state,
            county = county,
            postalcode = postalcode
        )
        return response.body() ?: emptyList()
    }

    override suspend fun getInfoMarker(osmIds: String): List<InfoMarker> {
        val response = infoMapApi.getInfoMarker(osmIds = osmIds)
        return response.body() ?: emptyList()
    }

    override suspend fun getReverse(lat: String, lon: String): SearchResult {
        val response = infoMapApi.getReverse(lat = lat, lon = lon)
        return response.body() ?: SearchResult()
    }
}