package com.example.core_database_data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.core_database_data.proto.SettingsSerializer
import com.example.core_database_domain.model.Setting
import com.example.core_database_domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val context: Context
): SettingsRepository {
    override fun getSettings(): Flow<Setting> =
        context.settingDataStore.data
            .catch { exception ->
                if (exception is IOException){
                    emit(com.example.core_database_data.Setting.getDefaultInstance())
                }else{
                    throw exception
                }
            }.map {
                Setting(
                    theme = enumValueOf(it.theme)
                )
            }

    override suspend fun updateSettings(setting: Setting) {
        context.settingDataStore.updateData { settings ->
            settings.toBuilder()
                .setTheme(setting.theme.name)
                .build()
        }
    }

    companion object{
        private val Context.settingDataStore: DataStore<com.example.core_database_data.Setting> by dataStore(
            fileName = "settings_data_store",
            serializer = SettingsSerializer
        )
    }
}