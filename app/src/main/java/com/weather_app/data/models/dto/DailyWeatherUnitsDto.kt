package com.weather_app.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherUnitsDto(
    @SerialName("time") val time: String? = null,
    @SerialName("temperature_2m_max") val temperature2mMax: String? = null,
    @SerialName("temperature_2m_min") val temperature2mMin: String? = null,
    @SerialName("weather_code") val weatherCode: String? = null
)