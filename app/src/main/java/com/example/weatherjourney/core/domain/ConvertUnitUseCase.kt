package com.example.weatherjourney.core.domain

import com.example.weatherjourney.core.model.TemperatureUnit
import com.example.weatherjourney.core.model.TemperatureUnit.Celsius
import com.example.weatherjourney.core.model.TemperatureUnit.Fahrenheit
import com.example.weatherjourney.core.model.Weather
import com.example.weatherjourney.core.model.convertTemperature
import javax.inject.Inject

class ConvertUnitUseCase @Inject constructor() {

    operator fun invoke(
        weather: Weather,
        temperatureUnit: TemperatureUnit? = null,
    ): Weather {

        return weather.apply {
            temperatureUnit?.let { convertTemperatureIfNeeded(it) }
        }
    }

    private fun Weather.convertTemperatureIfNeeded(unit: TemperatureUnit): Weather {
        return when (unit) {
            Fahrenheit -> this.convertTemperature(::convertCelsiusToFahrenheit)
            Celsius -> this
        }
    }

    private fun convertCelsiusToFahrenheit(celsius: Double) = celsius * 9 / 5 + 32
}
