package com.example.weatherjourney.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weathers")
data class WeatherEntity(
    val latitude: Double,
    val longitude: Double,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
)
