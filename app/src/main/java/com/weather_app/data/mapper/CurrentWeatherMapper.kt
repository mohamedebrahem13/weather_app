package com.weather_app.data.mapper

import com.weather_app.common.data.mapper.Mapper
import com.weather_app.data.models.dto.CurrentDto
import com.weather_app.domain.models.CurrentWeather

object CurrentWeatherMapper : Mapper<CurrentDto, CurrentWeather, Unit>() {

    override fun dtoToDomain(model: CurrentDto): CurrentWeather = CurrentWeather(
        time = model.time.orEmpty(),
        isDay = model.isDay == 1,
        rain = model.rain ?: 0.0,
        surfacePressure = model.surfacePressure ?: 0.0,
        temperature = model.temperature2m ?: 0.0,
        weatherCode = model.weatherCode ?: 0,
        windSpeed = model.windSpeed10m ?: 0.0,
        apparentTemperature = model.apparentTemperature ?: 0.0,
        humidity = model.relativeHumidity2m ?: 0
    )
}