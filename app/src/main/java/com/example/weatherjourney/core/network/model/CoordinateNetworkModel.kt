package com.example.weatherjourney.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CoordinateNetworkModel(
    val latitude: Double,
    val longitude: Double
)
