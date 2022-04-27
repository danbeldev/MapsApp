package com.example.maps.di

import com.example.feature_map.viewModel.MapViewModel
import dagger.Component
import javax.inject.Singleton

@[Singleton Component(modules = [ApiModule::class])]
interface AppComponent{

    fun mapViewModel():MapViewModel
}