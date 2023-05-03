package com.afprojectmaps.core_database_domain.useCase.userProto

import com.afprojectmaps.core_database_domain.model.HomeUser
import com.afprojectmaps.core_database_domain.repository.UserProtoRepository
import javax.inject.Inject

class UpdateHomeUserUseCase @Inject constructor(
    private val userProtoRepository: UserProtoRepository
) {
    suspend operator fun invoke(homeUser: HomeUser){
        userProtoRepository.updateHomeUser(homeUser)
    }
}