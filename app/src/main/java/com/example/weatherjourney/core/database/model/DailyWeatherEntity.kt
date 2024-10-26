package com.example.weatherjourney.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weatherjourney.core.database.util.DoubleListHolder
import com.example.weatherjourney.core.database.util.IntListHolder
import com.example.weatherjourney.core.database.util.LongListHolder

@Entity(
    tableName = "DailyWeathers",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("locationId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DailyWeatherEntity(

    @PrimaryKey
    val locationId: Int,
    val time: LongListHolder,
    val weatherCodes: IntListHolder,
    val maxTemperatures: DoubleListHolder,
    val minTemperatures: DoubleListHolder,
)
