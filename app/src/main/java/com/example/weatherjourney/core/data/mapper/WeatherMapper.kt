package com.example.weatherjourney.core.data.mapper

import com.example.weatherjourney.core.database.model.DailyWeatherEntity
import com.example.weatherjourney.core.database.model.HourlyWeatherEntity
import com.example.weatherjourney.core.database.model.WeatherEntity
import com.example.weatherjourney.core.database.model.WeatherWithDetails
import com.example.weatherjourney.core.database.util.DoubleListHolder
import com.example.weatherjourney.core.database.util.IntListHolder
import com.example.weatherjourney.core.database.util.LongListHolder
import com.example.weatherjourney.core.model.DailyWeather
import com.example.weatherjourney.core.model.HourlyWeather
import com.example.weatherjourney.core.model.Weather
import com.example.weatherjourney.core.model.WeatherType

fun Weather.asEntity() = WeatherWithDetails(
    weather = WeatherEntity(
        latitude = coordinate.latitude,
        longitude = coordinate.longitude,
    ),
    hourly = hourlyForecasts.asEntity(id),
    daily = dailyForecasts.asEntity(id),
)

fun List<HourlyWeather>.asEntity(weatherId: Int) = HourlyWeatherEntity(
    epochTimes = LongListHolder(map { it.epochTime }),
    temperatures = DoubleListHolder(map { it.temp }),
    humidities = DoubleListHolder(map { it.humanity }),
    weatherCodes = IntListHolder(map { WeatherType.toWMO(it.weatherType) }),
    weatherId = weatherId
)

fun List<DailyWeather>.asEntity(weatherId: Int) = DailyWeatherEntity(
    epochTimes = LongListHolder(map { it.epochTime }),
    maxTemperatures = DoubleListHolder(map { it.maxTemp }),
    minTemperatures = DoubleListHolder(map { it.minTemp }),
    weatherCodes = IntListHolder(map { WeatherType.toWMO(it.weatherType) }),
    weatherId = weatherId
)
