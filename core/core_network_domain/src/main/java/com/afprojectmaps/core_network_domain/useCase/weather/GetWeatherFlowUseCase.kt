package com.afprojectmaps.core_network_domain.useCase.weather

import com.afprojectmaps.core_network_domain.common.Response
import com.afprojectmaps.core_network_domain.entities.weather.WeatherResult
import com.afprojectmaps.core_network_domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi
import java.lang.Exception
import javax.inject.Inject

class GetWeatherFlowUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    @ExperimentalSerializationApi
    operator fun invoke(lat:Double, lon:Double):Flow<Response<WeatherResult>> = flow{
        try {
            val response = weatherRepository.getWeather(lat = lat.toString(),lon = lon.toString())
            if (response == null)
                emit(Response.Loading<WeatherResult>())
            else
                emit(Response.Success(data = response))
        }catch (e:Exception){
            emit(Response.Error<WeatherResult>(message = e.message.toString()))
        }
    }
}

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    @ExperimentalSerializationApi
    suspend operator fun invoke(lat:Double, lon:Double):Response<WeatherResult> {
        return try {
            val response = weatherRepository.getWeather(lat = lat.toString(),lon = lon.toString())
            if (response == null)
                Response.Loading<WeatherResult>()
            else
                Response.Success(data = response)
        }catch (e:Exception){
            Response.Error<WeatherResult>(message = e.message.toString())
        }
    }
}