package com.afprojectmaps.core_database_domain.repository

import com.afprojectmaps.core_database_domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun addHistory(history: History)

    suspend fun deleteHistory(id:Int)

    fun getHistory(search:String?): Flow<List<History>>

    suspend fun deleteHistory()
}