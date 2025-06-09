package com.weather_app.domain.models

data class DailyWeatherResponse(
    val dailyUnits: DailyWeatherUnits,
    val daily: DailyWeather
)