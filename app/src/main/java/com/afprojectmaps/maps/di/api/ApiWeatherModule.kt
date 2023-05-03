package com.afprojectmaps.maps.di.api

import com.afprojectmaps.core_network_data.api.WeatherApi
import com.afprojectmaps.core_network_data.repositoryImpl.WeatherRepositoryImpl
import com.afprojectmaps.core_network_domain.repository.WeatherRepository
import com.afprojectmaps.maps.common.ConstantsUrl.WEATHER_BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
class ApiWeatherModule {

    @[Singleton Provides ExperimentalSerializationApi]
    fun providerWeatherRetrofit(
        contentType:MediaType,
        json: Json
    ): WeatherApi = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
        .create(WeatherApi::class.java)

    @[Singleton Provides]
    fun providerWeatherRepository(
        weatherApi: WeatherApi
    ):WeatherRepository = WeatherRepositoryImpl(weatherApi)

    @[Singleton Provides]
    fun providerContentType():MediaType = MediaType.parse("application/json")!!

    @[Singleton Provides]
    fun providerJson():Json = Json {
        ignoreUnknownKeys = true
    }
}