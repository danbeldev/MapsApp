package com.example.maps.di.proto

import android.content.Context
import com.example.core_database_data.repository.UserProtoRepositoryImpl
import com.example.core_database_domain.repository.UserProtoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserProtoModule {

    @[Singleton Provides]
    fun providerUserRepository(
        context: Context
    ):UserProtoRepository = UserProtoRepositoryImpl(
        context = context
    )
}