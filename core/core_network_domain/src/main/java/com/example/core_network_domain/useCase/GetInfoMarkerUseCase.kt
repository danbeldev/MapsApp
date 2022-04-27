package com.example.core_network_domain.useCase

import com.example.core_network_domain.entitier.infoMap.InfoMarker
import com.example.core_network_domain.repository.InfoMapRepository
import javax.inject.Inject

class GetInfoMarkerUseCase @Inject constructor(
    private val infoMapRepository: InfoMapRepository
) {
    suspend operator fun invoke(osmIds:String):List<InfoMarker>{
        return infoMapRepository.getInfoMarker(osmIds = osmIds)
    }
}