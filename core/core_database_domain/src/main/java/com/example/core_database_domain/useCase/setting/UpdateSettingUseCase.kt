package com.example.core_database_domain.useCase.setting

import com.example.core_database_domain.model.Setting
import com.example.core_database_domain.repository.SettingsRepository
import javax.inject.Inject

class UpdateSettingUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(setting: Setting){
        settingsRepository.updateSettings(setting)
    }
}