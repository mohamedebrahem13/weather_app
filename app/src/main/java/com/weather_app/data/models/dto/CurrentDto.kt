package com.weather_app.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentDto(
    @SerialName("time") val time: String? = null,
    @SerialName("interval") val interval: Int? = null,
    @SerialName("is_day") val isDay: Int? = null,
    @SerialName("rain") val rain: Double? = null,
    @SerialName("surface_pressure") val surfacePressure: Double? = null,
    @SerialName("temperature_2m") val temperature2m: Double? = null,
    @SerialName("weather_code") val weatherCode: Int? = null,
    @SerialName("wind_speed_10m") val windSpeed10m: Double? = null,
    @SerialName("apparent_temperature") val apparentTemperature: Double? = null,
    @SerialName("relative_humidity_2m") val relativeHumidity2m: Int? = null
)