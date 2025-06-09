package com.weather_app.di

import com.weather_app.data.repository.remote.WeatherRemoteDataSourceImpl
import com.weather_app.domain.repository.remote.WeatherRemoteDataSource
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                })
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.open-meteo.com"
                    encodedPath = "/v1/"
                }
            }
        }
    }
    single<WeatherRemoteDataSource> { WeatherRemoteDataSourceImpl(get()) }

}