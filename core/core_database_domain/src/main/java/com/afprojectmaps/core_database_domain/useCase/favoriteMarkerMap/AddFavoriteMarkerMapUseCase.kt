package com.afprojectmaps.core_database_domain.useCase.favoriteMarkerMap

import com.afprojectmaps.core_database_domain.model.FavoriteMarkerMap
import com.afprojectmaps.core_database_domain.repository.FavoriteMarkerMapRepository
import javax.inject.Inject

class AddFavoriteMarkerMapUseCase @Inject constructor(
    private val favoriteMarkerMapRepository: FavoriteMarkerMapRepository
) {
    suspend operator fun invoke(favoriteMarkerMap:FavoriteMarkerMap){
        favoriteMarkerMapRepository.addFavoriteMarkerMap(
            favoriteMarkerMap = favoriteMarkerMap
        )
    }
}