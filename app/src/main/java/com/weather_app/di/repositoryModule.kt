package com.weather_app.di

import com.weather_app.data.repository.WeatherRepositoryImpl
import com.weather_app.data.repository.location.FusedLocationProviderImpl
import com.weather_app.domain.repository.WeatherRepository
import com.weather_app.domain.repository.location.LocationProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single<LocationProvider> {
        FusedLocationProviderImpl(androidContext())
    }

    single<WeatherRepository> { WeatherRepositoryImpl(get(),get()) }
}
