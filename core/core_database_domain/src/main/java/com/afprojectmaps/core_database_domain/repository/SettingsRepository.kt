package com.afprojectmaps.core_database_domain.repository

import com.afprojectmaps.core_database_domain.model.Setting
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getSettings(): Flow<Setting>

    suspend fun updateSettings(setting:Setting)
}