package com.example.weatherjourney.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationNetworkModel(
    val countryCode: String,
    val city: String,
    val locality: String,
    val localityInfo: LocalityInfo,
    val coordinate: CoordinateNetworkModel? = null
) {
    fun getAddress() = "$locality, $city"
    fun getTimezone() = localityInfo.informative
        .find { it.description == "time zone" }
        ?.name.orEmpty()
}

@Serializable
data class LocalityInfo(
    val informative: List<InfoSample>
)

@Serializable
data class InfoSample(
    val name: String,
    val description: String? = null,
)
