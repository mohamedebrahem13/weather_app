package com.weather_app.domain.models

data class CurrentWeather(
    val time: String,
    val isDay: Boolean,
    val rain: Double,
    val surfacePressure: Double,
    val temperature: Double,
    val weatherCode: Int,
    val windSpeed: Double,
    val apparentTemperature: Double,
    val humidity: Int
)