package com.afprojectmaps.core_database_domain.useCase.history

import com.afprojectmaps.core_database_domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteHistoryBayIdUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(id:Int){
        historyRepository.deleteHistory(id)
    }
}