package com.weather_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather_app.common.data.Resource
import com.weather_app.domain.interactor.GetCurrentWeatherUseCase
import com.weather_app.domain.interactor.GetDailyWeatherUseCase
import com.weather_app.domain.interactor.GetHourlyWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class WeatherViewModel(
    private val getHourlyWeatherUseCase: GetHourlyWeatherUseCase,
    private val getDailyWeatherUseCase: GetDailyWeatherUseCase,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        fetchWeatherData()
    }
    fun getDayName(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val localDate = LocalDate.parse(date, formatter)
        return localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            combine(
                getHourlyWeatherUseCase(),
                getDailyWeatherUseCase(),
                getCurrentWeatherUseCase()
            ) { hourlyRes, dailyRes, currentRes ->
                Triple(hourlyRes, dailyRes, currentRes)
            }.collect { (hourlyResource, dailyResource, currentResource) ->

                when {
                    hourlyResource is Resource.Progress ||
                            dailyResource is Resource.Progress ||
                            currentResource is Resource.Progress -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }

                    hourlyResource is Resource.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = hourlyResource.exception.message ?: "Unknown error"
                        )
                    }

                    dailyResource is Resource.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = dailyResource.exception.message ?: "Unknown error"
                        )
                    }

                    currentResource is Resource.Failure -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = currentResource.exception.message ?: "Unknown error"
                        )
                    }

                    hourlyResource is Resource.Success &&
                            dailyResource is Resource.Success &&
                            currentResource is Resource.Success -> {
                        val (hourlyWeather, city) = hourlyResource.model
                        val dailyWeather = dailyResource.model
                        val currentWeather = currentResource.model

                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            hourlyWeather = hourlyWeather,
                            city = city,
                            dailyWeather = dailyWeather,
                            currentWeather = currentWeather,
                            errorMessage = ""
                        )
                    }
                }
            }
        }
    }
}