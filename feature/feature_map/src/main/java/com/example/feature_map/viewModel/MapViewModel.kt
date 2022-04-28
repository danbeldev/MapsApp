package com.example.feature_map.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_network_domain.common.Response
import com.example.core_network_domain.entities.infoMap.InfoMarker
import com.example.core_network_domain.entities.infoMap.SearchResult
import com.example.core_network_domain.useCase.infoMap.GetInfoMarkerUseCase
import com.example.core_network_domain.useCase.infoMap.GetReverseUseCase
import com.example.core_network_domain.useCase.infoMap.GetSearchUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val getReverseUseCase: GetReverseUseCase,
    private val gerInfoMarkerUseCase: GetInfoMarkerUseCase
):ViewModel() {

    private val _responseSearch:MutableStateFlow<Response<List<SearchResult>>> =
        MutableStateFlow(Response.Loading())
    val responseSearch = _responseSearch.asStateFlow()

    private val _responseReverse:MutableStateFlow<SearchResult?> = MutableStateFlow(null)
    val responseReverse = _responseReverse.asStateFlow().filterNotNull()

    fun getSearch(
        city:String,
        county:String,
        country:String,
        postalcode:String
    ){
        getSearchUseCase.invoke(
            city, county, country, postalcode
        ).onEach {
            _responseSearch.value = it
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

    fun gerInfoMarker(osmIds:String):Flow<List<InfoMarker>> = flow{
        try {
            val response = gerInfoMarkerUseCase.invoke(osmIds = osmIds)
            Log.e("Retrofit", response.toString())
            emit(response)
        }catch (e:Exception){
            emit(emptyList())
            Log.e("Retrofit", e.message.toString())
        }
    }
}