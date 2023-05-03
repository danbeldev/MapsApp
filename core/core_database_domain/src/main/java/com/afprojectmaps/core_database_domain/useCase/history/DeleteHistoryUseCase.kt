package com.afprojectmaps.core_database_domain.useCase.history

import com.afprojectmaps.core_database_domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(){
        historyRepository.deleteHistory()
    }
}