package com.example.core_database_domain.repository

import com.example.core_database_domain.model.FavoriteMarkerMap
import kotlinx.coroutines.flow.Flow

interface FavoriteMarkerMapRepository {

    suspend fun addFavoriteMarkerMap(favoriteMarkerMap: FavoriteMarkerMap)

    fun getFavoriteMarkerMap(search:String?):Flow<List<FavoriteMarkerMap>>

    suspend fun deleteFavoriteMarkerMapById(id:Int)

    suspend fun deleteFavoriteMarkerMap()
}