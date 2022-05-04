package com.example.core_database_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Favorite_marker_map"
)
data class FavoriteMarkerMap(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "title") val title:String,
    @ColumnInfo(name = "latitude") val lat:Double,
    @ColumnInfo(name = "longitude") val lon:Double
)