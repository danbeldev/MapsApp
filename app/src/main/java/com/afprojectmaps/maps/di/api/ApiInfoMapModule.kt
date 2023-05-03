package com.afprojectmaps.maps.di.api

import com.afprojectmaps.core_network_data.api.InfoMapApi
import com.afprojectmaps.core_network_data.repositoryImpl.InfoMapRepositoryImpl
import com.afprojectmaps.core_network_domain.repository.InfoMapRepository
import com.afprojectmaps.maps.common.ConstantsUrl.INFO_MAP_BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiInfoMapModule {

    @[Provides Singleton]
    fun providerInfoMapRepository(
        infoMapApi: InfoMapApi
    ):InfoMapRepository = InfoMapRepositoryImpl(infoMapApi)

    @[Provides Singleton]
    fun providerInfoMapRetrofit(): InfoMapApi = Retrofit.Builder()
        .baseUrl(INFO_MAP_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(InfoMapApi::class.java)
}