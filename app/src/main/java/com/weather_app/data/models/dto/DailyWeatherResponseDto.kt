package com.weather_app.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherResponseDto(
    @SerialName("daily_units") val dailyUnits: DailyWeatherUnitsDto? = null,
    @SerialName("daily") val daily: DailyWeatherDto? = null
)