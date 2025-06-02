package com.example.weatherjourney.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weatherjourney.core.database.util.DoubleListHolder
import com.example.weatherjourney.core.database.util.IntListHolder
import com.example.weatherjourney.core.database.util.LongListHolder
import com.example.weatherjourney.core.model.DailyWeather
import com.example.weatherjourney.core.model.WeatherType

@Entity(
    tableName = "DailyWeathers",
    foreignKeys = [
        ForeignKey(
            entity = WeatherEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("weatherId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DailyWeatherEntity(
    val epochTimes: LongListHolder,
    val weatherCodes: IntListHolder,
    val maxTemperatures: DoubleListHolder,
    val minTemperatures: DoubleListHolder,

    @PrimaryKey
    val weatherId: Int = 0,
)

fun DailyWeatherEntity.asDailyForecasts(): List<DailyWeather> {
    return epochTimes.list.mapIndexed { index, time ->
        DailyWeather(
            epochTime = time,
            maxTemp = maxTemperatures.list[index],
            minTemp = minTemperatures.list[index],
            weatherType = WeatherType.fromWMO(weatherCodes.list[index]),
        )
    }
}
