package com.example.maps.di

import com.example.feature_map.viewModel.MapViewModel
import com.example.feature_weather.viewModel.WeatherViewModel
import dagger.Component
import javax.inject.Singleton

@[Singleton Component(modules = [ApiInfoMapModule::class, ApiWeatherModule::class, ApiRouteModule::class])]
interface AppComponent{

    fun mapViewModel():MapViewModel

    fun weatherViewModel():WeatherViewModel
}