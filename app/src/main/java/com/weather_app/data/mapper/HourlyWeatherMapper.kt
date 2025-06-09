package com.weather_app.data.mapper

import com.weather_app.common.data.mapper.Mapper
import com.weather_app.data.models.dto.HourlyDto
import com.weather_app.data.models.dto.HourlyWeatherDto
import com.weather_app.domain.models.HourlyData
import com.weather_app.domain.models.HourlyWeather

object HourlyWeatherMapper : Mapper<HourlyWeatherDto, HourlyWeather, Unit>() {

    override fun dtoToDomain(model: HourlyWeatherDto): HourlyWeather = HourlyWeather(
        hourly = model.hourly?.let { dtoToDomain(it) } ?: HourlyData(
            time = emptyList(),
            temperature2m = emptyList(),
            weatherCode = emptyList(),
            uvIndex = emptyList()
        )
    )

    fun dtoToDomain(dto: HourlyDto): HourlyData = HourlyData(
        time = dto.time.orEmpty(),
        temperature2m = dto.temperature2m.orEmpty(),
        weatherCode = dto.weatherCode.orEmpty(),
        uvIndex = dto.uvIndex.orEmpty()
    )
}
