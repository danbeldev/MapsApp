package com.afprojectmaps.maps.di.api

import com.afprojectmaps.core_network_data.api.RouteApi
import com.afprojectmaps.core_network_data.repositoryImpl.RouteRepositoryImpl
import com.afprojectmaps.core_network_domain.repository.RouteRepository
import com.afprojectmaps.maps.common.ConstantsUrl.ROUTE_BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiRouteModule {

    @[Singleton Provides]
    fun providerApiRoute():RouteApi = Retrofit.Builder()
        .baseUrl(ROUTE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RouteApi::class.java)

    @[Singleton Provides]
    fun providerApiRouteRepository(
        routeApi: RouteApi
    ):RouteRepository = RouteRepositoryImpl(routeApi)
}