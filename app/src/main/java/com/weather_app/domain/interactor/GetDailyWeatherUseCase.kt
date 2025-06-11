package com.weather_app.domain.interactor

import com.weather_app.common.domain.Resource
import com.weather_app.common.domain.WeatherDomainException
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
        } catch (e: WeatherDomainException) {
            emit(Resource.Failure(e))
        } catch (e: Exception) {
            emit(Resource.Failure(WeatherDomainException.GenericError(e.message)))
        }
    }
}