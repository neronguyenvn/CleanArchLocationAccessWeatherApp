package com.example.weatherjourney.core.model

data class Location(
    val id: Int,
    val address: String,
    val countryCode: String,
    val timeZone: String,
    val coordinate: Coordinate,
)
