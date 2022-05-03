package com.example.core_database_data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "History"
)
data class History(
     @PrimaryKey(autoGenerate = true) val id:Int,
     @ColumnInfo(name = "latitude") val lat:Double,
     @ColumnInfo(name = "longitude") val lon:Double,
     @ColumnInfo(name = "name") val name:String,
     @ColumnInfo(name = "transport") val transport:String
)