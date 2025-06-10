package com.weather_app.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weather_app.R
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

    fun getWeatherIcon(weatherCode: Int, isDay: Boolean): Int {
        val dayWeatherIcons = mapOf(
            0 to R.drawable.clear_sky,
            1 to R.drawable.mainly_clear,
            2 to R.drawable.partly_cloudy,
            3 to R.drawable.overcast,
            45 to R.drawable.fog,
            48 to R.drawable.depositing_rime_fog,
            51 to R.drawable.drizzle_light,
            53 to R.drawable.drizzle_moderate,
            55 to R.drawable.drizzle_intensity,
            61 to R.drawable.rain_slight,
            63 to R.drawable.rain_moderate,
            65 to R.drawable.rain_intensity,
            71 to R.drawable.snow_fall_light,
            73 to R.drawable.snow_fall_moderate,
            75 to R.drawable.snow_fall_intensity,
            80 to R.drawable.snow_shower_slight,
            81 to R.drawable.rain_shower_moderate,
            82 to R.drawable.rain_shower_violent,
            85 to R.drawable.rain_shower_slight,
            86 to R.drawable.snow_shower_heavy,
            95 to R.drawable.thunder_storm_slight_or_moderate,
            96 to R.drawable.thunder_storm_with_slight_hail,
            99 to R.drawable.thunder_storm_with_heavy_hail
        )

        val nightWeatherIcons = mapOf(
            0 to R.drawable.clear_sky_night,
            1 to R.drawable.mainly_clear_night,
            2 to R.drawable.partly_cloudy_night,
            3 to R.drawable.overcast_night,
            45 to R.drawable.fog_night,
            48 to R.drawable.depositing_rime_night,
            51 to R.drawable.drizzle_light_night,
            53 to R.drawable.drizzle_moderate_night,
            55 to R.drawable.drizzle_intensity_night,
            61 to R.drawable.rain_slight_night,
            63 to R.drawable.rain_moderate_night,
            65 to R.drawable.rain_intensity_night,
            71 to R.drawable.snow_fall_light_night,
            73 to R.drawable.snow_fall_moderate_night,
            75 to R.drawable.snow_fall_intensity_night,
            80 to R.drawable.rain_shower_slight_night,
            81 to R.drawable.rain_shower_moderate_night,
            82 to R.drawable.rain_shower_violent_night,
            85 to R.drawable.snow_shower_slight_night,
            86 to R.drawable.snow_shower_heavy_night,
            95 to R.drawable.thunderstrom_slight_or_moderate_night,
            96 to R.drawable.thunderstrom_with_heavy_hail_night,
            99 to R.drawable.thunderstrom_with_slight_hail_night
        )

        return if (isDay) {
            dayWeatherIcons.getOrElse(weatherCode) { R.drawable.clear_sky }
        } else {
            nightWeatherIcons.getOrElse(weatherCode) { R.drawable.clear_sky_night }
        }
    }
    @Composable
    fun getWeatherCondition(weatherCode: Int): String {

        return when (weatherCode) {
            0 -> stringResource(R.string.clear_sky)
            1 -> stringResource(R.string.mainly_clear)
            2 -> stringResource(R.string.partly_cloudy)
            3 -> stringResource(R.string.overcast)
            45 -> stringResource(R.string.fog)
            48 -> stringResource(R.string.depositing_rime_fog)
            51 -> stringResource(R.string.light_drizzle)
            53 -> stringResource(R.string.moderate_drizzle)
            55 -> stringResource(R.string.dense_drizzle)
            61 -> stringResource(R.string.light_rain)
            63 -> stringResource(R.string.moderate_rain)
            65 -> stringResource(R.string.heavy_rain)
            71 -> stringResource(R.string.light_snowfall)
            73 -> stringResource(R.string.moderate_snowfall)
            75 -> stringResource(R.string.heavy_snowfall)
            80 -> stringResource(R.string.rain_showers_slight)
            81 -> stringResource(R.string.rain_showers_moderate)
            82 -> stringResource(R.string.rain_showers_heavy)
            85 -> stringResource(R.string.snow_showers_slight)
            86 -> stringResource(R.string.snow_showers_heavy)
            95 -> stringResource(R.string.thunderstorms_slight)
            96 -> stringResource(R.string.thunderstorms_moderate)
            99 -> stringResource(R.string.thunderstorms_with_hail)
            else -> stringResource(R.string.weather_not_recognized)
        }
    }


    fun getDayWeatherIcon(weatherCode: Int): Int {
        val dayWeatherIcons = mapOf(
            0 to R.drawable.clear_sky,
            1 to R.drawable.mainly_clear,
            2 to R.drawable.partly_cloudy,
            3 to R.drawable.overcast,
            45 to R.drawable.fog,
            48 to R.drawable.depositing_rime_fog,
            51 to R.drawable.drizzle_light,
            53 to R.drawable.drizzle_moderate,
            55 to R.drawable.drizzle_intensity,
            61 to R.drawable.rain_slight,
            63 to R.drawable.rain_moderate,
            65 to R.drawable.rain_intensity,
            71 to R.drawable.snow_fall_light,
            73 to R.drawable.snow_fall_moderate,
            75 to R.drawable.snow_fall_intensity,
            80 to R.drawable.snow_shower_slight,
            81 to R.drawable.rain_shower_moderate,
            82 to R.drawable.rain_shower_violent,
            85 to R.drawable.rain_shower_slight,
            86 to R.drawable.snow_shower_heavy,
            95 to R.drawable.thunder_storm_slight_or_moderate,
            96 to R.drawable.thunder_storm_with_slight_hail,
            99 to R.drawable.thunder_storm_with_heavy_hail
        )

        return dayWeatherIcons.getOrElse(weatherCode) { R.drawable.clear_sky }
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