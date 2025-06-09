package com.weather_app.data.repository.remote

import com.weather_app.data.models.dto.CurrentWeatherResponseDto
import com.weather_app.data.models.dto.DailyWeatherResponseDto
import com.weather_app.data.models.dto.HourlyWeatherDto
import com.weather_app.domain.repository.remote.WeatherRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class WeatherRemoteDataSourceImpl(
    private val client: HttpClient
) : WeatherRemoteDataSource {
    override suspend fun getHourlyWeather(
        latitude: String,
        longitude: String
    ): HourlyWeatherDto {
        return client.get("forecast") {
            url {
                parameters.append("latitude", latitude)
                parameters.append("longitude", longitude)
                parameters.append("hourly", "temperature_2m,weather_code,uv_index")
            }
        }.body()
    }

    override suspend fun getDailyWeather(
        latitude: String,
        longitude: String
    ): DailyWeatherResponseDto {
        return client.get("forecast") {
            url {
                parameters.append("latitude", latitude)
                parameters.append("longitude", longitude)
                parameters.append(
                    "daily",
                    "temperature_2m_max,temperature_2m_min,weather_code"
                )
                parameters.append("timezone", "auto")
            }
        }.body()
    }
    override suspend fun getCurrentWeather(
        latitude: String,
        longitude: String
    ): CurrentWeatherResponseDto {
        return client.get("forecast") {
            url {
                parameters.append("latitude", latitude)
                parameters.append("longitude", longitude)
                parameters.append("current", "temperature_2m,weather_code,wind_speed_10m,apparent_temperature,relative_humidity_2m,is_day,rain,surface_pressure")
                parameters.append("timezone", "auto")
            }
        }.body()
    }
}
