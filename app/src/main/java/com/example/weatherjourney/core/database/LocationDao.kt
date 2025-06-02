package com.example.weatherjourney.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.weatherjourney.core.database.model.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Query("SELECT * FROM Locations WHERE latitude = :latitude AND longitude = :longitude")
    fun observe(latitude: Double, longitude: Double): Flow<LocationEntity>

    @Query("SELECT * FROM Locations")
    suspend fun getAll(): List<LocationEntity>

    @Query("SELECT * FROM Locations WHERE id = :id")
    suspend fun getById(id: Int): LocationEntity?

    @Insert
    suspend fun insert(locationEntity: LocationEntity): Long

    @Query("DELETE FROM Locations WHERE id = :id")
    suspend fun deleteById(id: Long): Int
}
