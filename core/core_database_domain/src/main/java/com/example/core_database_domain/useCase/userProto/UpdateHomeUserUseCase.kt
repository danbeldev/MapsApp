package com.example.core_database_domain.useCase.userProto

import com.example.core_database_domain.model.HomeUser
import com.example.core_database_domain.repository.UserProtoRepository
import javax.inject.Inject

class UpdateHomeUserUseCase @Inject constructor(
    private val userProtoRepository: UserProtoRepository
) {
    suspend operator fun invoke(homeUser: HomeUser){
        userProtoRepository.updateHomeUser(homeUser)
    }
}