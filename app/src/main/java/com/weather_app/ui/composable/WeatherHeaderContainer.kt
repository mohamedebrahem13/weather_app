package com.weather_app.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weather_app.R
import com.weather_app.domain.models.HourlyWeather
import com.weather_app.ui.viewmodel.WeatherViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherHeaderContainer(
    isDay: Boolean,
    hourlyWeather: HourlyWeather,
    city: String,
    scrollOffset: Float,
    viewModel: WeatherViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.location_05),
                contentDescription = stringResource(R.string.location_icon),
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = city,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        val currentTime = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00"))
        val currentIndex = hourlyWeather.hourly.time.indexOfFirst { it == currentTime }

        val temperature = hourlyWeather.hourly.temperature2m.getOrElse(currentIndex) { "--" }.toString()
        val weatherCode = hourlyWeather.hourly.weatherCode.getOrElse(currentIndex) { 0 }
        val weatherIcon = viewModel.getWeatherIcon(weatherCode, isDay)
        val maxTemperature = hourlyWeather.hourly.temperature2m.maxOrNull()?.toString() ?: "--"
        val minTemperature = hourlyWeather.hourly.temperature2m.minOrNull()?.toString() ?: "--"
        val condition = viewModel.getWeatherCondition(weatherCode)

        CollapsingWeatherContent(
            temperature = temperature,
            weatherIcon = weatherIcon,
            maxTemperature = maxTemperature,
            minTemperature = minTemperature,
            scrollOffset = scrollOffset,
            weatherCondition = condition
        )
    }
}