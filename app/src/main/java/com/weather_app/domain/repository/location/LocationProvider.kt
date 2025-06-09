package com.weather_app.domain.repository.location

import com.weather_app.data.models.dto.LocationDto

interface LocationProvider {
    suspend fun getCurrentLocation(): LocationDto?
}