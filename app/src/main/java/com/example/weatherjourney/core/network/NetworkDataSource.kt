package com.example.weatherjourney.core.network

import com.example.weatherjourney.core.model.Coordinate
import com.example.weatherjourney.core.network.model.LocationNetworkModel
import com.example.weatherjourney.core.network.model.NetworkLocation
import com.example.weatherjourney.core.network.model.WeatherNetworkModel

interface NetworkDataSource {

    suspend fun getWeather(coordinate: Coordinate): WeatherNetworkModel

    suspend fun searchLocationsByAddress(address: String): List<NetworkLocation>

    suspend fun getLocation(coordinate: Coordinate): LocationNetworkModel
}
