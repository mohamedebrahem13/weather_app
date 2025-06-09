package com.weather_app.data.mapper

import com.weather_app.common.data.mapper.Mapper
import com.weather_app.data.models.dto.LocationDto
import com.weather_app.domain.models.Location

object LocationMapper : Mapper<LocationDto, Location, Unit>() {

    override fun dtoToDomain(model: LocationDto): Location {
        return Location(
            latitude = model.latitude ?: 0.0,
            longitude = model.longitude ?: 0.0,
            city = model.city.orEmpty(),
            country = model.country.orEmpty()
        )
    }
}