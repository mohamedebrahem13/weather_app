package com.weather_app.domain.models

data class HourlyData(
    val time: List<String>,
    val temperature2m: List<Double>,
    val weatherCode: List<Int>,
    val uvIndex: List<Double>
)
