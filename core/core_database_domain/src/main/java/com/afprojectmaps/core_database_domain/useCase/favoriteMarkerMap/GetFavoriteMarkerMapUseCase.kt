package com.afprojectmaps.core_database_domain.useCase.favoriteMarkerMap

import com.afprojectmaps.core_database_domain.model.FavoriteMarkerMap
import com.afprojectmaps.core_database_domain.repository.FavoriteMarkerMapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMarkerMapUseCase @Inject constructor(
    private val favoriteMarkerMapRepository: FavoriteMarkerMapRepository
) {
    operator fun invoke(search:String?):Flow<List<FavoriteMarkerMap>> {
        return favoriteMarkerMapRepository.getFavoriteMarkerMap(search)
    }
}