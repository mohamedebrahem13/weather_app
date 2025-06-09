package com.weather_app.data.mapper

import com.weather_app.common.data.mapper.Mapper
import com.weather_app.data.models.dto.DailyWeatherDto
import com.weather_app.data.models.dto.DailyWeatherResponseDto
import com.weather_app.data.models.dto.DailyWeatherUnitsDto
import com.weather_app.domain.models.DailyWeather
import com.weather_app.domain.models.DailyWeatherResponse
import com.weather_app.domain.models.DailyWeatherUnits

object DailyWeatherMapper : Mapper<DailyWeatherResponseDto, DailyWeatherResponse, Unit>() {

    override fun dtoToDomain(model: DailyWeatherResponseDto): DailyWeatherResponse = DailyWeatherResponse(
        dailyUnits = model.dailyUnits?.let { dtoToDomain(it) } ?: DailyWeatherUnits("", "", "", ""),
        daily = model.daily?.let { dtoToDomain(it) } ?: DailyWeather(
            time = emptyList(),
            temperature2mMax = emptyList(),
            temperature2mMin = emptyList(),
            weatherCode = emptyList()
        )
    )

    fun dtoToDomain(model: DailyWeatherUnitsDto): DailyWeatherUnits = DailyWeatherUnits(
        time = model.time.orEmpty(),
        temperature2mMax = model.temperature2mMax.orEmpty(),
        temperature2mMin = model.temperature2mMin.orEmpty(),
        weatherCode = model.weatherCode.orEmpty()
    )

    fun dtoToDomain(model: DailyWeatherDto): DailyWeather = DailyWeather(
        time = model.time ?: emptyList(),
        temperature2mMax = model.temperature2mMax ?: emptyList(),
        temperature2mMin = model.temperature2mMin ?: emptyList(),
        weatherCode = model.weatherCode ?: emptyList()
    )
}