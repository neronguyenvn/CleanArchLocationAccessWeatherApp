package com.example.weatherjourney.core.data.mapper

import com.example.weatherjourney.core.database.model.DailyWeatherEntity
import com.example.weatherjourney.core.database.model.HourlyWeatherEntity
import com.example.weatherjourney.core.database.model.LocationEntity
import com.example.weatherjourney.core.database.model.WeatherEntity
import com.example.weatherjourney.core.database.model.WeatherWithDetails
import com.example.weatherjourney.core.database.util.DoubleListHolder
import com.example.weatherjourney.core.database.util.IntListHolder
import com.example.weatherjourney.core.database.util.LongListHolder
import com.example.weatherjourney.core.model.Coordinate
import com.example.weatherjourney.core.model.Location
import com.example.weatherjourney.core.network.model.CoordinateNetworkModel
import com.example.weatherjourney.core.network.model.DailyWeatherNetworkModel
import com.example.weatherjourney.core.network.model.HourlyWeatherNetworkModel
import com.example.weatherjourney.core.network.model.LocationNetworkModel
import com.example.weatherjourney.core.network.model.WeatherNetworkModel

fun Location.asEntity() = LocationEntity(
    address = address,
    countryCode = countryCode,
    timeZone = timeZone,
    latitude = coordinate.latitude,
    longitude = coordinate.longitude,
)

fun LocationNetworkModel.asEntity() = LocationEntity(
    address = getAddress(),
    countryCode = countryCode,
    timeZone = getTimezone(),
    latitude = coordinate!!.latitude,
    longitude = coordinate.longitude,
)

fun Coordinate.asNetworkModel() = CoordinateNetworkModel(
    latitude = latitude,
    longitude = longitude,
)

fun WeatherNetworkModel.asEntity() = WeatherWithDetails(
    weather = WeatherEntity(
        latitude = coordinate!!.latitude,
        longitude = coordinate.longitude,
    ),
    hourly = hourly.asEntity(),
    daily = daily.asEntity()
)

fun HourlyWeatherNetworkModel.asEntity(): HourlyWeatherEntity {
    val hourlyWeatherEntity = HourlyWeatherEntity(
        epochTimes = LongListHolder(time),
        temperatures = DoubleListHolder(temperatures),
        humidities = DoubleListHolder(humanities),
        weatherCodes = IntListHolder(weatherCodes),
    )
    return hourlyWeatherEntity
}

fun DailyWeatherNetworkModel.asEntity() = DailyWeatherEntity(
    epochTimes = LongListHolder(time),
    maxTemperatures = DoubleListHolder(maxTemperatures),
    minTemperatures = DoubleListHolder(minTemperatures),
    weatherCodes = IntListHolder(weatherCodes),
)
