package com.example.core_network_domain.useCase.weather

import com.example.core_network_domain.common.Response
import com.example.core_network_domain.entities.weather.WeatherResult
import com.example.core_network_domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
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