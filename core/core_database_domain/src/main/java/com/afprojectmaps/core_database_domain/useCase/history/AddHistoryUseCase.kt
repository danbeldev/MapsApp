package com.afprojectmaps.core_database_domain.useCase.history

import com.afprojectmaps.core_database_domain.model.History
import com.afprojectmaps.core_database_domain.repository.HistoryRepository
import javax.inject.Inject

class AddHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(history: History){
        historyRepository.addHistory(history)
    }
}