package com.weather_app.android

import android.app.Application
import com.weather_app.di.networkModule
import com.weather_app.di.repositoryModule
import com.weather_app.di.uiModule
import com.weather_app.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApp)
            modules(listOf(networkModule,useCaseModule, repositoryModule,uiModule))
        }
    }
}