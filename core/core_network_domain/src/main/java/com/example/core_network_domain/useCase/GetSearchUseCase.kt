package com.example.core_network_domain.useCase

import com.example.core_network_domain.entitier.infoMap.SearchResult
import com.example.core_network_domain.repository.InfoMapRepository
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(
    private val infoMapRepository: InfoMapRepository
) {
    suspend operator fun invoke(
        city:String,
        county:String,
        state:String,
        country:String,
        postalcode:String
    ):List<SearchResult>{
        return infoMapRepository.getSearch(city, county, state, country, postalcode)
    }
}