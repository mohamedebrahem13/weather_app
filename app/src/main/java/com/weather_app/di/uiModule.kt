package com.weather_app.di

import com.weather_app.ui.viewmodel.WeatherViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel { WeatherViewModel(getHourlyWeatherUseCase = get(), getDailyWeatherUseCase = get (), getCurrentWeatherUseCase = get ()) }
}