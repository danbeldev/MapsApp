package com.afprojectmaps.core_database_domain.useCase.userProto

import com.afprojectmaps.core_database_domain.model.WorkUser
import com.afprojectmaps.core_database_domain.repository.UserProtoRepository
import javax.inject.Inject

class UpdateWorkUserUseCase @Inject constructor(
    private val userProtoRepository: UserProtoRepository
) {
    suspend operator fun invoke(workUser: WorkUser){
        userProtoRepository.updateWorkUser(workUser)
    }
}