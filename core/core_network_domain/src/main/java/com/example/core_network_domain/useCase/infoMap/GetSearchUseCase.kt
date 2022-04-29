package com.example.core_network_domain.useCase.infoMap

import com.example.core_network_domain.common.Response
import com.example.core_network_domain.entities.infoMap.SearchResult
import com.example.core_network_domain.repository.InfoMapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchUseCase @Inject constructor(
    private val infoMapRepository: InfoMapRepository
) {
    operator fun invoke(
        city:String,
        county:String,
        country:String,
        postalcode:String,
        street:String
    ): Flow<Response<List<SearchResult>>> = flow {
        try {
            val response = infoMapRepository.getSearch(city, county, country, postalcode,street)
            emit(Response.Success(data = response))
        }catch (e:Exception){
            emit(Response.Error<List<SearchResult>>(message = e.message.toString()))
        }
    }
}