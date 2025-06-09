package com.weather_app.data.repository.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.LocationServices
import com.weather_app.data.models.dto.LocationDto
import com.weather_app.domain.repository.location.LocationProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FusedLocationProviderImpl(
    private val context: Context
) : LocationProvider {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val geocoder = Geocoder(context, Locale.getDefault())

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): LocationDto? = suspendCancellableCoroutine { cont ->

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    try {
                        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val city = addresses?.firstOrNull()?.locality.orEmpty()
                        val country = addresses?.firstOrNull()?.countryName.orEmpty()

                        cont.resume(
                            LocationDto(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                city = city,
                                country = country
                            )
                        )
                    } catch (e: Exception) {

                        cont.resume(
                            LocationDto(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                city = "",
                                country = ""
                            )
                        )
                    }
                } else {
                    cont.resume(null)
                }
            }
            .addOnFailureListener { exception ->
                cont.resumeWithException(exception)
            }
    }
}