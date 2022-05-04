package com.example.maps.di.database

import android.content.Context
import androidx.room.Room
import com.example.core_database_data.database.UserDatabase
import com.example.core_database_data.repository.FavoriteMarkerMapRepositoryImpl
import com.example.core_database_data.repository.HistoryRepositoryImpl
import com.example.core_database_domain.repository.FavoriteMarkerMapRepository
import com.example.core_database_domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserDatabaseModule {

    @[Provides Singleton]
    fun providerHistoryRepository(
        userDatabase: UserDatabase
    ):HistoryRepository = HistoryRepositoryImpl(
        historyDao = userDatabase.historyDao()
    )

    @[Provides Singleton]
    fun providerFavoriteMarkerMapRepository(
        userDatabase: UserDatabase
    ): FavoriteMarkerMapRepository = FavoriteMarkerMapRepositoryImpl(
        favoriteMarkerMapDao = userDatabase.favoriteMarkerMapDao()
    )

    @[Provides Singleton]
    fun providerUserDatabase(
        context: Context
    ):UserDatabase = Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        "user_database"
    ).build()
}