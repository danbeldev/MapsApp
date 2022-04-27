package com.example.core_network_domain.useCase

import com.example.core_network_domain.entities.infoMap.SearchResult
import com.example.core_network_domain.repository.InfoMapRepository
import javax.inject.Inject

class GetReverseUseCase @Inject constructor(
    private val infoMapRepository: InfoMapRepository
) {
    suspend operator fun invoke(lat:String, lon:String): SearchResult {
        return infoMapRepository.getReverse(lat, lon)
    }
}