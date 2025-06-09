package com.weather_app.domain.models

data class DailyWeather(
    val time: List<String>,
    val temperature2mMax: List<Double>,
    val temperature2mMin: List<Double>,
    val weatherCode: List<Int>
)