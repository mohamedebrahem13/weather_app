package com.weather_app.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DailyWeatherDto(
    @SerialName("time") val time: List<String>? = null,
    @SerialName("temperature_2m_max") val temperature2mMax: List<Double>? = null,
    @SerialName("temperature_2m_min") val temperature2mMin: List<Double>? = null,
    @SerialName("weather_code") val weatherCode: List<Int>? = null
)