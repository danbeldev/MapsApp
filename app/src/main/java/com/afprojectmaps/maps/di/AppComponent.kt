package com.afprojectmaps.maps.di

import android.content.Context
import com.afprojectmaps.core_network_domain.useCase.weather.GetWeatherUseCase
import com.afprojectmaps.feature_map.viewModel.MapViewModel
import com.afprojectmaps.feature_settings.viewModel.SettingViewModel
import com.afprojectmaps.feature_weather.viewModel.WeatherViewModel
import com.afprojectmaps.maps.di.api.ApiInfoMapModule
import com.afprojectmaps.maps.di.api.ApiRouteModule
import com.afprojectmaps.maps.di.api.ApiWeatherModule
import com.afprojectmaps.maps.di.database.UserDatabaseModule
import com.afprojectmaps.maps.di.proto.SettingsProtoModule
import com.afprojectmaps.maps.di.proto.UserProtoModule
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
    SettingsProtoModule::class,
    UserProtoModule::class
])]
interface AppComponent{

    fun mapViewModel():MapViewModel

    fun settingViewModel():SettingViewModel

    fun weatherViewModel():WeatherViewModel

    fun getWeatherUseCase(): GetWeatherUseCase

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun context(context: Context):Builder

        fun build():AppComponent
    }
}