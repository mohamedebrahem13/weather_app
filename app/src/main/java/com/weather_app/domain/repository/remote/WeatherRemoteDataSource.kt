package com.weather_app.domain.repository.remote

import com.weather_app.data.models.dto.CurrentWeatherResponseDto
import com.weather_app.data.models.dto.DailyWeatherResponseDto
import com.weather_app.data.models.dto.HourlyWeatherDto

interface WeatherRemoteDataSource {
    suspend fun getHourlyWeather(
        latitude: String,
        longitude: String,
    ): HourlyWeatherDto
    suspend fun getDailyWeather(
        latitude: String,
        longitude: String,
    ): DailyWeatherResponseDto
    suspend fun getCurrentWeather(latitude: String, longitude: String): CurrentWeatherResponseDto

}