package com.weather_app.ui.viewmodel

import com.weather_app.domain.models.CurrentWeather
import com.weather_app.domain.models.DailyWeatherResponse
import com.weather_app.domain.models.HourlyWeather

data class WeatherUiState(
    val isLoading: Boolean = false,
    val hourlyWeather: HourlyWeather? = null,
    val currentWeather: CurrentWeather? = null,
    val city: String = "",
    val dailyWeather: DailyWeatherResponse? = null,
    val errorMessage: String = ""
)