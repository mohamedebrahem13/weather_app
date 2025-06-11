package com.weather_app.common.data


sealed class WeatherDataException(message: String?) : Exception(message) {

    sealed class Location(message: String?) : WeatherDataException(message) {
        data object Unavailable : Location("Unable to get device location.")
        data object PermissionDenied : Location("Location permission is denied.")
    }

    sealed class Network(message: String?) : WeatherDataException(message) {
        object NoInternet : Network("No internet connection.")
        data class Timeout(
            val duration: Int,
            override val message: String? = "Request timed out."
        ) :
            Network(message)

        data class Unexpected(val code: Int, override val message: String?) : Network(message)
    }

    sealed class Data(message: String?) : WeatherDataException(message) {
        data object WeatherMissing : Data("Weather data is missing.")
        data object ParsingError : Data("Failed to parse weather data.")
    }

    data object Unknown : WeatherDataException("Unknown error occurred.")
}