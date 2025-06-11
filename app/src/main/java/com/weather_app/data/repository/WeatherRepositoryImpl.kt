package com.weather_app.data.repository

import com.weather_app.common.data.WeatherDataException
import com.weather_app.common.domain.WeatherDomainException
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
        return try {
            val locationDto = locationProvider.getCurrentLocation()
            val location = LocationMapper.dtoToDomain(locationDto)

            val latStr = location.latitude.toString()
            val lonStr = location.longitude.toString()

            val dto = remoteDataSource.getHourlyWeather(latStr, lonStr)
            val weatherResponse = HourlyWeatherMapper.dtoToDomain(dto)

            weatherResponse to location.city
        } catch (e: WeatherDataException) {
            throw mapToDomainException(e)
        }
    }

    override suspend fun getDailyWeather(): DailyWeatherResponse {
        return try {
            val locationDto = locationProvider.getCurrentLocation()
            val location = LocationMapper.dtoToDomain(locationDto)

            val latStr = location.latitude.toString()
            val lonStr = location.longitude.toString()

            val dto = remoteDataSource.getDailyWeather(latStr, lonStr)
            DailyWeatherMapper.dtoToDomain(dto)
        } catch (e: WeatherDataException) {
            throw mapToDomainException(e)
        }
    }

    override suspend fun getCurrentWeather(): CurrentWeather {
        return try {
            val locationDto = locationProvider.getCurrentLocation()
            val location = LocationMapper.dtoToDomain(locationDto)

            val latStr = location.latitude.toString()
            val lonStr = location.longitude.toString()

            val dto = remoteDataSource.getCurrentWeather(latStr, lonStr)
            dto.current?.let { CurrentWeatherMapper.dtoToDomain(it) }
                ?: throw WeatherDataException.Data.WeatherMissing
        } catch (e: WeatherDataException) {
            throw mapToDomainException(e)
        }
    }

    private fun mapToDomainException(e: WeatherDataException): WeatherDomainException {
        return when (e) {
            is WeatherDataException.Location.PermissionDenied ->
                WeatherDomainException.LocationPermissionDenied()

            is WeatherDataException.Location.Unavailable ->
                WeatherDomainException.LocationError()

            is WeatherDataException.Network.NoInternet ->
                WeatherDomainException.NoInternet()

            is WeatherDataException.Network.Timeout,
            is WeatherDataException.Network.Unexpected ->
                WeatherDomainException.GenericError()

            is WeatherDataException.Data.WeatherMissing,
            is WeatherDataException.Data.ParsingError ->
                WeatherDomainException.GenericError()

            is WeatherDataException.Unknown ->
                WeatherDomainException.GenericError()
        }
    }
}