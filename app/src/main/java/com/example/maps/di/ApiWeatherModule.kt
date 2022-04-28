package com.example.maps.di

import com.example.core_network_data.api.WeatherApi
import com.example.core_network_data.repositoryImpl.WeatherRepositoryImpl
import com.example.core_network_domain.repository.WeatherRepository
import com.example.maps.common.ConstantsUrl.WEATHER_BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiWeatherModule {

    @[Singleton Provides]
    fun providerWeatherRetrofit(): WeatherApi = Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    @[Singleton Provides]
    fun providerWeatherRepository(
        weatherApi: WeatherApi
    ):WeatherRepository = WeatherRepositoryImpl(weatherApi)

}