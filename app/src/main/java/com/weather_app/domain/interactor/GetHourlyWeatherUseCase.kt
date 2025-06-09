package com.weather_app.domain.interactor

import com.weather_app.common.data.Resource
import com.weather_app.domain.models.HourlyWeather
import com.weather_app.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHourlyWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): Flow<Resource<Pair<HourlyWeather, String>>> = flow {
        emit(Resource.Progress(loading = true))
        try {
            val weatherDataWithCity = repository.getHourlyWeather()
            emit(Resource.Success(weatherDataWithCity))
        } catch (e: Exception) {
            emit(Resource.Failure(Exception("Failed to fetch weather data: ${e.message}", e)))
        }
    }
}