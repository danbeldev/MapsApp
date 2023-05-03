package com.afprojectmaps.core_database_data.repository

import com.afprojectmaps.core_database_data.dao.FavoriteMarkerMapDao
import com.afprojectmaps.core_database_domain.model.FavoriteMarkerMap
import com.afprojectmaps.core_database_domain.repository.FavoriteMarkerMapRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteMarkerMapRepositoryImpl @Inject constructor(
    private val favoriteMarkerMapDao: FavoriteMarkerMapDao
): FavoriteMarkerMapRepository {
    override suspend fun addFavoriteMarkerMap(favoriteMarkerMap: FavoriteMarkerMap) {
        favoriteMarkerMapDao.addFavoriteMarkerMap(
            favoriteMarkerMap = com.afprojectmaps.core_database_data.model.FavoriteMarkerMap(
                id = favoriteMarkerMap.id,
                title = favoriteMarkerMap.title,
                lat = favoriteMarkerMap.lat,
                lon = favoriteMarkerMap.lon
            )
        )
    }

    override fun getFavoriteMarkerMap(search: String?): Flow<List<FavoriteMarkerMap>> {
        return favoriteMarkerMapDao.getFavoriteMarkerMap(search)
            .map {
                it.map { favoriteMarkerMap ->
                    FavoriteMarkerMap(
                        id = favoriteMarkerMap.id,
                        title = favoriteMarkerMap.title,
                        lon = favoriteMarkerMap.lon,
                        lat = favoriteMarkerMap.lat
                    )
                }
            }
    }

    override suspend fun deleteFavoriteMarkerMapById(id: Int) {
        favoriteMarkerMapDao.deleteFavoriteMarkerMapById(id)
    }

    override suspend fun deleteFavoriteMarkerMap() {
        favoriteMarkerMapDao.deleteFavoriteMarkerMap()
    }
}