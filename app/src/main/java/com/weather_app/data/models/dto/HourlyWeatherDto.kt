package com.weather_app.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourlyWeatherDto(
    @SerialName("hourly")
    val hourly: HourlyDto? = null
)