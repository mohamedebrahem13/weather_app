package com.weather_app.di

import com.weather_app.domain.interactor.GetCurrentWeatherUseCase
import com.weather_app.domain.interactor.GetDailyWeatherUseCase
import com.weather_app.domain.interactor.GetHourlyWeatherUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetHourlyWeatherUseCase(repository = get()) }
    single { GetDailyWeatherUseCase(repository = get()) }
    single { GetCurrentWeatherUseCase(repository = get()) }
}