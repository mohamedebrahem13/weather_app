package com.weather_app.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.weather_app.domain.models.HourlyWeather
import com.weather_app.ui.screens.getWeatherIcon
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HourlyWeatherRow(hourlyWeather: HourlyWeather, isDay: Boolean) {
    val times = hourlyWeather.hourly.time
    val temps = hourlyWeather.hourly.temperature2m
    val weatherCodes = hourlyWeather.hourly.weatherCode

    val today = LocalDate.now()
    DateTimeFormatter.ISO_LOCAL_DATE

    val filteredData = times.mapIndexedNotNull { index, timeStr ->
        val dateTime = LocalDateTime.parse(timeStr)
        if (dateTime.toLocalDate() == today) {
            Triple(dateTime, temps[index], weatherCodes[index])
        } else {
            null
        }
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(filteredData.size) { index ->
            val (dateTime, temp, weatherCode) = filteredData[index]
            Box(modifier = Modifier.wrapContentSize()) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
                            RoundedCornerShape(20.dp)
                        )
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(
                            top = 62.dp,
                            bottom = 8.dp,
                            start = 26.dp,
                            end = 25.dp
                        ),
                        text = "$temp°C",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f)
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = dateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
                Image(
                    painter = painterResource(getWeatherIcon(weatherCode, isDay = isDay)),
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(width = 63.99.dp, height = 58.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = (-12).dp)
                )
            }
        }
    }
}
