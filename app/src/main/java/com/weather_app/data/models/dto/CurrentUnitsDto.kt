package com.weather_app.data.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentUnitsDto(
    @SerialName("time") val time: String? = null,
    @SerialName("interval") val interval: String? = null,
    @SerialName("is_day") val isDay: String? = null,
    @SerialName("rain") val rain: String? = null,
    @SerialName("surface_pressure") val surfacePressure: String? = null,
    @SerialName("temperature_2m") val temperature2m: String? = null,
    @SerialName("weather_code") val weatherCode: String? = null,
    @SerialName("wind_speed_10m") val windSpeed10m: String? = null,
    @SerialName("apparent_temperature") val apparentTemperature: String? = null,
    @SerialName("relative_humidity_2m") val relativeHumidity2m: String? = null
)