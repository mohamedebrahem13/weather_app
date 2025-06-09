package com.weather_app.domain.models

data class Location(
    val latitude: Double,
    val longitude: Double,
    val city: String ,
    val country: String
)