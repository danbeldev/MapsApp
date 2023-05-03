package com.afprojectmaps.core_network_domain.useCase.weather

import com.afprojectmaps.core_network_domain.entities.weather.WeatherAlert
import com.afprojectmaps.core_network_domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetWeatherAlertsUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(lat:Double, lon:Double):Flow<WeatherAlert> = flow {
        val response = weatherRepository.getWeatherAlerts(lat.toString(), lon.toString())
        emit(response ?: WeatherAlert())
    }
}