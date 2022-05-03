package com.example.core_database_domain.useCase.history

import com.example.core_database_domain.model.History
import com.example.core_database_domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    operator fun invoke(search:String?):Flow<List<History>>{
        return historyRepository.getHistory(search)
    }
}