package com.example.core_database_domain.useCase.userProto

import com.example.core_database_domain.model.HomeUser
import com.example.core_database_domain.repository.UserProtoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHomeUserUseCase @Inject constructor(
    private val userProtoRepository: UserProtoRepository
) {
    operator fun invoke():Flow<HomeUser>{
        return userProtoRepository.getHomeUser()
    }
}