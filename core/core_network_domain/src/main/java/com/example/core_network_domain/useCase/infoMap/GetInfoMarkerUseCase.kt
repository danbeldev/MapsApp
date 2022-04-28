package com.example.core_network_domain.useCase.infoMap

import com.example.core_network_domain.entities.infoMap.InfoMarker
import com.example.core_network_domain.repository.InfoMapRepository
import javax.inject.Inject

class GetInfoMarkerUseCase @Inject constructor(
    private val infoMapRepository: InfoMapRepository
) {
    suspend operator fun invoke(osmIds:String):List<InfoMarker>{
        return infoMapRepository.getInfoMarker(osmIds = osmIds)
    }
}