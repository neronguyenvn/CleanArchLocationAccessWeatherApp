package com.example.weatherjourney.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.weatherjourney.core.common.util.filterPastHours
import com.example.weatherjourney.core.database.util.DoubleListHolder
import com.example.weatherjourney.core.database.util.IntListHolder
import com.example.weatherjourney.core.database.util.LongListHolder
import com.example.weatherjourney.core.model.CurrentWeather
import com.example.weatherjourney.core.model.HourlyWeather
import com.example.weatherjourney.core.model.WeatherType

@Entity(
    tableName = "HourlyWeathers",
    foreignKeys = [
        ForeignKey(
            entity = WeatherEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("weatherId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HourlyWeatherEntity(
    val epochTimes: LongListHolder,
    val humidities: DoubleListHolder,
    val temperatures: DoubleListHolder,
    val weatherCodes: IntListHolder,

    @PrimaryKey
    val weatherId: Int = 0,
)

fun HourlyWeatherEntity.asCurrentWeather(count: Int): CurrentWeather {
    return CurrentWeather(
        temp = temperatures.list[count],
        humidity = humidities.list[count],
        weatherType = WeatherType.fromWMO(weatherCodes.list[count]),
    )
}

fun HourlyWeatherEntity.asHourlyForecasts(count: Int): List<HourlyWeather> {
    return epochTimes.list.filterPastHours().mapIndexed { index, time ->
        val actualIndex = index + count
        HourlyWeather(
            epochTime = time,
            temp = temperatures.list[actualIndex],
            humanity = humidities.list[actualIndex],
            weatherType = WeatherType.fromWMO(weatherCodes.list[actualIndex]),
        )
    }
}
