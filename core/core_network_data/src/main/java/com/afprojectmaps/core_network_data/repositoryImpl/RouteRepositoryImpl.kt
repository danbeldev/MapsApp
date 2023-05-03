package com.afprojectmaps.core_network_data.repositoryImpl

import com.afprojectmaps.core_network_data.api.RouteApi
import com.afprojectmaps.core_network_domain.entities.route.Route
import com.afprojectmaps.core_network_domain.repository.RouteRepository
import javax.inject.Inject

class RouteRepositoryImpl @Inject constructor(
    private val routeApi: RouteApi
):RouteRepository {
    override suspend fun getRoute(profile: String, start: String, end: String): Route? {
        return routeApi.getRoute(profile = profile, start = start, end = end).body()
    }
}