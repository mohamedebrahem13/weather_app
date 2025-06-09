package com.weather_app.data.repository

import com.weather_app.data.mapper.CurrentWeatherMapper
import com.weather_app.data.mapper.DailyWeatherMapper
import com.weather_app.data.mapper.LocationMapper
import com.weather_app.data.mapper.HourlyWeatherMapper
import com.weather_app.domain.models.CurrentWeather
import com.weather_app.domain.models.DailyWeatherResponse
import com.weather_app.domain.models.HourlyWeather
import com.weather_app.domain.repository.WeatherRepository
import com.weather_app.domain.repository.location.LocationProvider
import com.weather_app.domain.repository.remote.WeatherRemoteDataSource


class WeatherRepositoryImpl(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val locationProvider: LocationProvider
) : WeatherRepository {

    override suspend fun getHourlyWeather(): Pair<HourlyWeather, String> {
        val locationDto = locationProvider.getCurrentLocation()
            ?: throw IllegalStateException("Location unavailable")

        val location = LocationMapper.dtoToDomain(locationDto)
        val latStr = location.latitude.toString()
        val lonStr = location.longitude.toString()

        val dto = remoteDataSource.getHourlyWeather(latStr, lonStr)
        val weatherResponse = HourlyWeatherMapper.dtoToDomain(dto)

        return weatherResponse to location.city
    }

    override suspend fun getDailyWeather(): DailyWeatherResponse {
        val locationDto = locationProvider.getCurrentLocation()
            ?: throw IllegalStateException("Location unavailable")

        val location = LocationMapper.dtoToDomain(locationDto)
        val latStr = location.latitude.toString()
        val lonStr = location.longitude.toString()

        val dto = remoteDataSource.getDailyWeather(latStr, lonStr)
        return DailyWeatherMapper.dtoToDomain(dto)
    }
    override suspend fun getCurrentWeather(): CurrentWeather {
        val locationDto = locationProvider.getCurrentLocation()
            ?: throw IllegalStateException("Location unavailable")

        val location = LocationMapper.dtoToDomain(locationDto)
        val latStr = location.latitude.toString()
        val lonStr = location.longitude.toString()

        val dto = remoteDataSource.getCurrentWeather(latStr, lonStr)
        val weather = dto.current?.let { CurrentWeatherMapper.dtoToDomain(it) }
            ?: throw IllegalStateException("Current weather data missing")

        return weather
    }
}