package com.example.core_database_domain.useCase.favoriteMarkerMap

import com.example.core_database_domain.repository.FavoriteMarkerMapRepository
import javax.inject.Inject

class DeleteFavoriteMarkerMapUseCase @Inject constructor(
    private val favoriteMarkerMapRepository: FavoriteMarkerMapRepository
) {
    suspend operator fun invoke(){
        favoriteMarkerMapRepository.deleteFavoriteMarkerMap()
    }
}