package com.example.weatherjourney.core.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.weatherjourney.core.common.util.countPastHoursToday
import com.example.weatherjourney.core.model.Coordinate
import com.example.weatherjourney.core.model.Weather

data class WeatherWithDetails(
    @Embedded val weather: WeatherEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "weatherId"
    )
    val hourly: HourlyWeatherEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "weatherId"
    )
    val daily: DailyWeatherEntity
)

fun WeatherWithDetails.asWeather(): Weather {
    val count = hourly.epochTimes.list.countPastHoursToday()
    return Weather(
        id = weather.id,
        coordinate = Coordinate.create(weather.latitude, weather.longitude),
        current = hourly.asCurrentWeather(count),
        hourlyForecasts = hourly.asHourlyForecasts(count),
        dailyForecasts = daily.asDailyForecasts(),
    )
}

