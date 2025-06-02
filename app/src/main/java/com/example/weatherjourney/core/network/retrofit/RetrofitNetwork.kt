package com.example.weatherjourney.core.network.retrofit

import com.example.weatherjourney.core.data.mapper.asNetworkModel
import com.example.weatherjourney.core.model.Coordinate
import com.example.weatherjourney.core.network.NetworkDataSource
import com.example.weatherjourney.core.network.model.LocationNetworkModel
import com.example.weatherjourney.core.network.model.NetworkLocation
import com.example.weatherjourney.core.network.model.SearchLocationsResponse
import com.example.weatherjourney.core.network.model.WeatherNetworkModel
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

private const val BIGDATACLOUD_BASE_URL = "https://us1.api-bdc.net/data/"
private const val OPENMETEO_GET_WEATHER_URL = "https://api.open-meteo.com/v1/forecast/"
private const val OPENMETEO_GET_GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search/"

private interface RetrofitWtnNetworkApi {

    @GET("reverse-geocode-client")
    suspend fun getReverseGeocoding(
        @Query("latitude")
        lat: Double,

        @Query("longitude")
        long: Double,
    ): LocationNetworkModel

    @GET
    suspend fun getAllWeather(
        @Url
        url: String = OPENMETEO_GET_WEATHER_URL,

        @Query("latitude")
        lat: Double,

        @Query("longitude")
        long: Double,

        @Query("hourly", encoded = true)
        hourlyParams: String = "temperature_2m,relativehumidity_2m,weathercode,pressure_msl,windspeed_10m",

        @Query("daily", encoded = true)
        dailyParams: String = "weathercode,temperature_2m_max,temperature_2m_min",

        @Query("timeformat")
        timeFormat: String = "unixtime",
    ): WeatherNetworkModel

    @GET
    suspend fun searchLocationsByAddress(
        @Url url: String = OPENMETEO_GET_GEOCODING_URL,
        @Query("name", encoded = true) address: String,
    ): SearchLocationsResponse
}

class RetrofitNetwork(
    networkJson: Json,
    okHttpCallFactory: Call.Factory
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BIGDATACLOUD_BASE_URL)
        .callFactory(okHttpCallFactory)
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(RetrofitWtnNetworkApi::class.java)

    override suspend fun getWeather(coordinate: Coordinate): WeatherNetworkModel =
        networkApi
            .getAllWeather(
                lat = coordinate.latitude,
                long = coordinate.longitude,
            )
            .copy(coordinate = coordinate.asNetworkModel())

    override suspend fun searchLocationsByAddress(address: String): List<NetworkLocation> {
        return networkApi.searchLocationsByAddress(address = address).results
    }

    override suspend fun getLocation(coordinate: Coordinate): LocationNetworkModel =
        networkApi
            .getReverseGeocoding(coordinate.latitude, coordinate.longitude)
            .copy(coordinate = coordinate.asNetworkModel())
}
