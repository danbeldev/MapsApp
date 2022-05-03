package com.example.core_database_data.repository

import com.example.core_database_data.dao.HistoryDao
import com.example.core_database_domain.model.History
import com.example.core_database_domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
): HistoryRepository {
    override suspend fun addHistory(history: History) {
        historyDao.addHistory(
            com.example.core_database_data.model.History(
                id = history.id,
                lat = history.lat,
                lon = history.lon,
                name = history.name,
                transport = history.transport
            )
        )
    }

    override suspend fun deleteHistory(id:Int) {
        historyDao.deleteHistory(id)
    }

    override suspend fun deleteHistory() {
        historyDao.deleteHistory()
    }

    override fun getHistory(search:String?): Flow<List<History>> {
        return historyDao.getHistory(search).map {
            it.map { history ->
                History(
                    id = history.id,
                    lat = history.lat,
                    lon = history.lon,
                    name = history.name,
                    transport = history.transport
                )
            }
        }
    }
}