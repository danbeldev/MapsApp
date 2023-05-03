package com.afprojectmaps.core_database_data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.afprojectmaps.core_database_data.model.FavoriteMarkerMap
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMarkerMapDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteMarkerMap(favoriteMarkerMap: FavoriteMarkerMap)

    @Query("SELECT * FROM favorite_marker_map WHERE title LIKE '%' || :search || '%' ORDER BY id DESC")
    fun getFavoriteMarkerMap(search:String?):Flow<List<FavoriteMarkerMap>>

    @Query("DELETE FROM favorite_marker_map WHERE id = :id")
    suspend fun deleteFavoriteMarkerMapById(id:Int)

    @Query("DELETE FROM favorite_marker_map")
    suspend fun deleteFavoriteMarkerMap()
}