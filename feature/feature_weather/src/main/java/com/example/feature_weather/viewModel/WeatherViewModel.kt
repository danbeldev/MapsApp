package com.example.feature_weather.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_network_domain.common.Response
import com.example.core_network_domain.entities.infoMap.SearchResult
import com.example.core_network_domain.entities.weather.WeatherAlert
import com.example.core_network_domain.entities.weather.WeatherResult
import com.example.core_network_domain.useCase.infoMap.GetReverseUseCase
import com.example.core_network_domain.useCase.weather.GetWeatherAlertsUseCase
import com.example.core_network_domain.useCase.weather.GetWeatherDailyHourlyUseCase
import com.example.core_network_domain.useCase.weather.GetWeatherUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val getWeather: GetWeatherUseCase,
    private val getReverseUseCase: GetReverseUseCase,
    private val getWeatherAlertsUseCase: GetWeatherAlertsUseCase,
    private val getWeatherDailyHourlyUseCase: GetWeatherDailyHourlyUseCase
):ViewModel() {

    private val _responseWeather:MutableStateFlow<Response<WeatherResult>> = MutableStateFlow(Response.Loading())
    val responseWeather = _responseWeather.asStateFlow()

    private val _responseReverse:MutableStateFlow<SearchResult?> = MutableStateFlow(null)
    val responseReverse = _responseReverse.asStateFlow().filterNotNull()

    private val _responseWeatherAlerts:MutableStateFlow<WeatherAlert?> = MutableStateFlow(null)
    val responseWeatherAlerts = _responseWeatherAlerts.asStateFlow().filterNotNull()

    private val _responseWeatherDailyHourly:MutableStateFlow<WeatherAlert?> = MutableStateFlow(null)
    val responseWeatherDailyHourly = _responseWeatherDailyHourly.asStateFlow().filterNotNull()

    fun getWeather(lat:Double, lon:Double){
        getWeather.invoke(lat,lon).onEach {
            _responseWeather.value = it
            Log.e("Retrofit:", it.message.toString())
        }.launchIn(viewModelScope)
    }

    fun getReverse(
        lat:String,
        lon:String
    ){
        viewModelScope.launch {
            try {
                val response = getReverseUseCase.invoke(
                    lat, lon
                )
                _responseReverse.value = response
            }catch (e:Exception){
                Log.e("Retrofit", e.message.toString())
            }
        }
    }

    fun getWeatherAlerts(
        lat:Double,
        lon:Double
    ){
        getWeatherAlertsUseCase.invoke(lat, lon).onEach {
            _responseWeatherAlerts.value = it
        }.launchIn(viewModelScope)
    }

    fun getWeatherDailyHourly(
        lat:Double,
        lon:Double
    ){
        getWeatherDailyHourlyUseCase.invoke(lat, lon).onEach {
            _responseWeatherDailyHourly.value = it
        }.launchIn(viewModelScope)
    }
}