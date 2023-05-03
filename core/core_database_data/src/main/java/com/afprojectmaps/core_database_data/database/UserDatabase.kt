package com.afprojectmaps.core_database_data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.afprojectmaps.core_database_data.dao.FavoriteMarkerMapDao
import com.afprojectmaps.core_database_data.dao.HistoryDao
import com.afprojectmaps.core_database_data.model.FavoriteMarkerMap
import com.afprojectmaps.core_database_data.model.History

@Database(
    version = 2,
    entities = [
        History::class,
        FavoriteMarkerMap::class
    ], autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2
        )
    ]
)
abstract class UserDatabase:RoomDatabase() {

    abstract fun historyDao():HistoryDao

    abstract fun favoriteMarkerMapDao(): FavoriteMarkerMapDao
}