package com.example.maps.di

import com.example.core_network_domain.useCase.weather.GetWeatherUseCase
import com.example.feature_map.viewModel.MapViewModel
import com.example.feature_weather.viewModel.WeatherViewModel
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@[Singleton Component(modules = [ApiInfoMapModule::class, ApiWeatherModule::class, ApiRouteModule::class])]
interface AppComponent{

    fun mapViewModel():MapViewModel

    @ExperimentalSerializationApi
    fun weatherViewModel():WeatherViewModel

    fun getWeatherUseCase(): GetWeatherUseCase
}