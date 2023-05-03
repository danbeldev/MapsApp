package com.afprojectmaps.feature_settings.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afprojectmaps.core_database_domain.model.Setting
import com.afprojectmaps.core_database_domain.useCase.favoriteMarkerMap.DeleteFavoriteMarkerMapUseCase
import com.afprojectmaps.core_database_domain.useCase.history.DeleteHistoryUseCase
import com.afprojectmaps.core_database_domain.useCase.setting.GetSettingUseCase
import com.afprojectmaps.core_database_domain.useCase.setting.UpdateSettingUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    getSettingUseCase: GetSettingUseCase,
    private val updateSettingUseCase: UpdateSettingUseCase,
    private val deleteHistoryUseCase: DeleteHistoryUseCase,
    private val deleteFavoriteMarkerMapUseCase: DeleteFavoriteMarkerMapUseCase
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

    fun deleteFavoriteMarkerMap(){
        viewModelScope.launch {
            deleteFavoriteMarkerMapUseCase.invoke()
        }
    }
}