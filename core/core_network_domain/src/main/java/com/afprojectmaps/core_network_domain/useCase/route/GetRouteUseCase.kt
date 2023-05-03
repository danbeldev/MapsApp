package com.afprojectmaps.core_network_domain.useCase.route

import com.afprojectmaps.core_network_domain.common.Response
import com.afprojectmaps.core_network_domain.entities.route.Route
import com.afprojectmaps.core_network_domain.repository.RouteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetRouteUseCase @Inject constructor(
    private val routeRepository: RouteRepository
) {
    suspend operator fun invoke(profile:String, start:String, end:String):Flow<Response<Route>> = flow{
        try {
            val response = routeRepository.getRoute(profile, start, end)
            response?.let {
                emit(Response.Success(data = it))
            }
            emit(Response.Error<Route>(message = "null"))
        }catch (e:Exception){
            emit(Response.Error<Route>(message = e.message.toString()))
        }
    }
}