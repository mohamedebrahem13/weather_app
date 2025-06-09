package com.weather_app.domain.interactor

import com.weather_app.common.data.Resource
import com.weather_app.domain.models.CurrentWeather
import com.weather_app.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCurrentWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): Flow<Resource<CurrentWeather>> = flow {
        emit(Resource.Progress(loading = true))
        try {
            val currentWeather = repository.getCurrentWeather()
            emit(Resource.Success(currentWeather))
        } catch (e: Exception) {
            emit(Resource.Failure(Exception("Failed to fetch current weather data: ${e.message}", e)))
        }
    }
}