package com.example.maps.di.proto

import android.content.Context
import com.example.core_database_data.repository.SettingsRepositoryImpl
import com.example.core_database_domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsProtoModule {

    @[Provides Singleton]
    fun providerSettingsRepository(
        context: Context
    ):SettingsRepository = SettingsRepositoryImpl(context)
}