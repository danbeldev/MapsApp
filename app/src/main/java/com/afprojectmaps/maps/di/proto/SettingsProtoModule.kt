package com.afprojectmaps.maps.di.proto

import android.content.Context
import com.afprojectmaps.core_database_data.repository.SettingsRepositoryImpl
import com.afprojectmaps.core_database_domain.repository.SettingsRepository
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