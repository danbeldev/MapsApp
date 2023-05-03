package com.afprojectmaps.core_network_data.repositoryImpl

import com.afprojectmaps.core_network_data.api.InfoMapApi
import com.afprojectmaps.core_network_domain.entities.infoMap.InfoMarker
import com.afprojectmaps.core_network_domain.entities.infoMap.SearchResult
import com.afprojectmaps.core_network_domain.repository.InfoMapRepository
import javax.inject.Inject

class InfoMapRepositoryImpl @Inject constructor (
    private val infoMapApi: InfoMapApi
):InfoMapRepository {
    override suspend fun getSearch(
        city: String,
        county: String,
        country: String,
        postalcode: String,
        street:String
    ): List<SearchResult> {
        val response = infoMapApi.getSearch(
            city = city,
            country = country,
            county = county,
            postalcode = postalcode,
            street = street
        )
        return response.body() ?: emptyList()
    }

    override suspend fun getInfoMarker(osmIds: String): List<InfoMarker> {
        val response = infoMapApi.getInfoMarker(osmIds = "R$osmIds")
        return response.body() ?: emptyList()
    }

    override suspend fun getReverse(lat: String, lon: String): SearchResult {
        val response = infoMapApi.getReverse(lat = lat, lon = lon)
        return response.body() ?: SearchResult()
    }
}