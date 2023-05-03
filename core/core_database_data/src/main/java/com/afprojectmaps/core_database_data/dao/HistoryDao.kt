package com.afprojectmaps.core_database_data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afprojectmaps.core_database_data.model.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addHistory(history: History)

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun deleteHistory(id:Int)

    @Query("SELECT * FROM history WHERE name LIKE '%' || :search || '%' ORDER BY id DESC")
    fun getHistory(search:String?):Flow<List<History>>

    @Query("DELETE FROM history")
    suspend fun deleteHistory()
}