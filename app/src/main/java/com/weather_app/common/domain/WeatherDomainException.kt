package com.weather_app.common.domain

sealed class WeatherDomainException(open val errorMessage: String?) : Exception(errorMessage) {

    data class NoInternet(override val errorMessage: String? = "No internet connection.") :
        WeatherDomainException(errorMessage)

    data class LocationPermissionDenied(override val errorMessage: String? = "Location permission is denied.") :
        WeatherDomainException(errorMessage)

    data class LocationError(override val errorMessage: String? = "Failed to get your location.") :
        WeatherDomainException(errorMessage)

    data class GenericError(override val errorMessage: String? = "Something went wrong.") :
        WeatherDomainException(errorMessage)
}
