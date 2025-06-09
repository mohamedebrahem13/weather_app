package com.weather_app.domain.interactor

import com.weather_app.common.data.Resource
import com.weather_app.domain.models.DailyWeatherResponse
import com.weather_app.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDailyWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(): Flow<Resource<DailyWeatherResponse>> = flow {
        emit(Resource.Progress(loading = true))
        try {
            val dailyWeather = repository.getDailyWeather()
            emit(Resource.Success(dailyWeather))
        } catch (e: Exception) {
            emit(Resource.Failure(Exception("Failed to fetch daily weather data: ${e.message}", e)))
        }
    }
}