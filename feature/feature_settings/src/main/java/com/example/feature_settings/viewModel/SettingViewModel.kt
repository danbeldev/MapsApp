package com.example.feature_settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_database_domain.model.Setting
import com.example.core_database_domain.useCase.history.DeleteHistoryUseCase
import com.example.core_database_domain.useCase.setting.GetSettingUseCase
import com.example.core_database_domain.useCase.setting.UpdateSettingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    getSettingUseCase: GetSettingUseCase,
    private val updateSettingUseCase: UpdateSettingUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase
):ViewModel() {

    val responseSetting:Flow<Setting> = getSettingUseCase.invoke()

    fun updateSetting(setting: Setting){
        viewModelScope.launch {
            updateSettingUseCase.invoke(setting)
        }
    }

    fun deleteHistory(){
        viewModelScope.launch {
            deleteHistoryUseCase.invoke()
        }
    }
}