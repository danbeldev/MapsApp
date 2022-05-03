package com.example.core_database_data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.core_database_data.dao.HistoryDao
import com.example.core_database_data.model.History

@Database(
    version = 1,
    entities = [
        History::class
    ]
)
abstract class UserDatabase:RoomDatabase() {

    abstract fun historyDao():HistoryDao
}