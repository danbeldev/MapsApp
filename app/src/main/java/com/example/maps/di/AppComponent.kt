package com.example.maps.di

import android.content.Context
import com.example.core_network_domain.useCase.weather.GetWeatherUseCase
import com.example.feature_map.viewModel.MapViewModel
import com.example.feature_weather.viewModel.WeatherViewModel
import com.example.maps.di.api.ApiInfoMapModule
import com.example.maps.di.api.ApiRouteModule
import com.example.maps.di.api.ApiWeatherModule
import com.example.maps.di.database.UserDatabaseModule
import com.example.maps.di.proto.SettingsProtoModule
import dagger.BindsInstance
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@[Singleton Component(modules = [
    ApiInfoMapModule::class,
    ApiWeatherModule::class,
    ApiRouteModule::class,
    UserDatabaseModule::class,
    SettingsProtoModule::class
])]
interface AppComponent{

    fun mapViewModel():MapViewModel

    @ExperimentalSerializationApi
    fun weatherViewModel():WeatherViewModel

    fun getWeatherUseCase(): GetWeatherUseCase

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context):Builder

        fun build():AppComponent
    }
}