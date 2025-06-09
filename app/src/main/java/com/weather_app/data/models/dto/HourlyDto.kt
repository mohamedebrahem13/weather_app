package com.weather_app.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyDto(
    @SerialName("time") val time: List<String>? = null,
    @SerialName("temperature_2m") val temperature2m: List<Double>? = null,
    @SerialName("weather_code") val weatherCode: List<Int>? = null,
    @SerialName("uv_index") val uvIndex: List<Double>? = null,
    @SerialName("is_day") val isDay: List<Int>? = null
)
