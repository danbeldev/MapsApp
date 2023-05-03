package com.afprojectmaps.core_database_domain.useCase.setting

import com.afprojectmaps.core_database_domain.model.Setting
import com.afprojectmaps.core_database_domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke():Flow<Setting>{
        return settingsRepository.getSettings()
    }
}