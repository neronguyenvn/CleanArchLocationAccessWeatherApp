package com.example.weatherjourney.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.weatherjourney.core.database.model.DailyWeatherEntity
import com.example.weatherjourney.core.database.model.HourlyWeatherEntity
import com.example.weatherjourney.core.database.model.WeatherEntity
import com.example.weatherjourney.core.database.model.WeatherWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Transaction
    @Query("SELECT * FROM Weathers WHERE latitude = :latitude AND longitude = :longitude")
    fun observe(latitude: Double, longitude: Double): Flow<WeatherWithDetails>

    @Insert
    suspend fun insert(weatherEntity: WeatherEntity): Long

    @Insert
    suspend fun insertHourly(hourlyWeatherEntity: HourlyWeatherEntity): Long

    @Insert
    suspend fun insertDaily(dailyWeatherEntity: DailyWeatherEntity): Long
}
