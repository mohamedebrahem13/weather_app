package com.weather_app.domain.repository

import com.weather_app.domain.models.CurrentWeather
import com.weather_app.domain.models.DailyWeatherResponse
import com.weather_app.domain.models.HourlyWeather

interface WeatherRepository {
    suspend fun getHourlyWeather(): Pair<HourlyWeather, String>
    suspend fun getDailyWeather(): DailyWeatherResponse
    suspend fun getCurrentWeather(): CurrentWeather

}
