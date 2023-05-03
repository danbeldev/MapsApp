package com.afprojectmaps.feature_map.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afprojectmaps.core_database_domain.model.FavoriteMarkerMap
import com.afprojectmaps.core_database_domain.model.History
import com.afprojectmaps.core_database_domain.model.HomeUser
import com.afprojectmaps.core_database_domain.model.WorkUser
import com.afprojectmaps.core_database_domain.useCase.favoriteMarkerMap.AddFavoriteMarkerMapUseCase
import com.afprojectmaps.core_database_domain.useCase.favoriteMarkerMap.DeleteFavoriteMarkerMapByIdUseCase
import com.afprojectmaps.core_database_domain.useCase.favoriteMarkerMap.GetFavoriteMarkerMapUseCase
import com.afprojectmaps.core_database_domain.useCase.history.AddHistoryUseCase
import com.afprojectmaps.core_database_domain.useCase.history.DeleteHistoryBayIdUseCase
import com.afprojectmaps.core_database_domain.useCase.history.GetHistoryUseCase
import com.afprojectmaps.core_database_domain.useCase.setting.GetSettingUseCase
import com.afprojectmaps.core_database_domain.useCase.userProto.GetHomeUserUseCase
import com.afprojectmaps.core_database_domain.useCase.userProto.GetWorkUserUseCase
import com.afprojectmaps.core_database_domain.useCase.userProto.UpdateHomeUserUseCase
import com.afprojectmaps.core_database_domain.useCase.userProto.UpdateWorkUserUseCase
import com.afprojectmaps.core_network_domain.common.Response
import com.afprojectmaps.core_network_domain.entities.infoMap.InfoMarker
import com.afprojectmaps.core_network_domain.entities.infoMap.SearchResult
import com.afprojectmaps.core_network_domain.entities.route.Route
import com.afprojectmaps.core_network_domain.useCase.infoMap.GetInfoMarkerUseCase
import com.afprojectmaps.core_network_domain.useCase.infoMap.GetReverseUseCase
import com.afprojectmaps.core_network_domain.useCase.infoMap.GetSearchUseCase
import com.afprojectmaps.core_network_domain.useCase.route.GetRouteUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val getSearchUseCase: GetSearchUseCase,
    private val getReverseUseCase: GetReverseUseCase,
    private val gerInfoMarkerUseCase: GetInfoMarkerUseCase,
    private val getRouteUseCase: GetRouteUseCase,
    private val addHistoryUseCase: AddHistoryUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val deleteHistoryBayIdUseCase: DeleteHistoryBayIdUseCase,
    private val updateHomeUserUseCase: UpdateHomeUserUseCase,
    private val updateWorkUserUseCase: UpdateWorkUserUseCase,
    private val addFavoriteMarkerMapUseCase: AddFavoriteMarkerMapUseCase,
    private val getFavoriteMarkerMapUseCase: GetFavoriteMarkerMapUseCase,
    private val deleteFavoriteMarkerMapByIdUseCase: DeleteFavoriteMarkerMapByIdUseCase,
    getHomeUserUseCase: GetHomeUserUseCase,
    getWorkUserUseCase: GetWorkUserUseCase,
    getSettingUseCase: GetSettingUseCase
):ViewModel() {

    private val _responseSearch:MutableStateFlow<Response<List<SearchResult>>> =
        MutableStateFlow(Response.Loading())
    val responseSearch = _responseSearch.asStateFlow()

    private val _responseReverse:MutableStateFlow<SearchResult?> = MutableStateFlow(null)
    val responseReverse = _responseReverse.asStateFlow().filterNotNull()

    private val _responseRoute:MutableStateFlow<Route?> = MutableStateFlow(null)
    val responseRoute = _responseRoute.asStateFlow().filterNotNull()

    private val _responseHistory:MutableStateFlow<List<History>> =
        MutableStateFlow(listOf())
    val responseHistory = _responseHistory.asStateFlow()

    private val _responseFavoriteMarkerMap = MutableStateFlow(listOf<FavoriteMarkerMap>())
    val responseFavoriteMarkerMap = _responseFavoriteMarkerMap.asStateFlow()

    val responseSetting = getSettingUseCase.invoke()

    val responseHomeUser = getHomeUserUseCase.invoke()

    val responseWorkUser = getWorkUserUseCase.invoke()

    fun getSearch(
        city:String,
        county:String,
        country:String,
        postalcode:String,
        street:String
    ){
        getSearchUseCase.invoke(
            city, county, country, postalcode, street
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
            emit(response)
        }catch (e:Exception){
            emit(emptyList())
            Log.e("Retrofit", e.message.toString())
        }
    }

    fun getRouteUseCase(profile:String, start:String, end:String){
        viewModelScope.launch {
            getRouteUseCase.invoke(profile, start, end).onEach {
                it.data?.let { route ->
                    _responseRoute.value = route
                }
            }.collect()
        }
    }

    fun addHistory(history: History){
        viewModelScope.launch {
            addHistoryUseCase.invoke(history)
        }
    }

    fun getHistory(search:String?){
        getHistoryUseCase.invoke(search).onEach {
            _responseHistory.value = it
        }.launchIn(viewModelScope)
    }

    fun deleteHistoryBayId(id:Int){
        viewModelScope.launch {
            deleteHistoryBayIdUseCase.invoke(id)
        }
    }

    fun updateHomeUser(homeUser: HomeUser){
        viewModelScope.launch {
            updateHomeUserUseCase.invoke(homeUser)
        }
    }

    fun updateWorkUser(workUser: WorkUser){
        viewModelScope.launch {
            updateWorkUserUseCase.invoke(workUser)
        }
    }

    fun addFavoriteMarkerMap(favoriteMarkerMap: FavoriteMarkerMap){
        viewModelScope.launch {
            addFavoriteMarkerMapUseCase.invoke(favoriteMarkerMap)
        }
    }

    fun getFavoriteMarkerMap(search:String?){
        getFavoriteMarkerMapUseCase.invoke(search).onEach {
            _responseFavoriteMarkerMap.value = it
        }.launchIn(viewModelScope)
    }

    fun deleteFavoriteMarkerMapById(id:Int){
        viewModelScope.launch {
            deleteFavoriteMarkerMapByIdUseCase.invoke(id = id)
        }
    }
}