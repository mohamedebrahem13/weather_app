package com.weather_app.common.domain

import com.weather_app.common.data.WeatherDataException

sealed class Resource<out Model> {
    data class Progress<Model>(val loading: Boolean, val partialData: Model? = null) :
        Resource<Model>()

    data class Success<out Model>(val model: Model) : Resource<Model>()
    data class Failure(val exception: WeatherDomainException) : Resource<Nothing>()

}