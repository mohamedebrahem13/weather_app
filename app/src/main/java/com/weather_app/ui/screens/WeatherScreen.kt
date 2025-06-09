package com.weather_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.weather_app.R
import com.weather_app.android.theme.LightBlue
import com.weather_app.domain.models.CurrentWeather
import com.weather_app.domain.models.DailyWeatherResponse
import com.weather_app.domain.models.HourlyWeather
import com.weather_app.ui.composable.WeatherMetricCard
import com.weather_app.ui.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeatherScreen(
    paddingValues: PaddingValues,
    onDayNightDetermined: (Boolean) -> Unit,
    viewModel: WeatherViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsState()
    LaunchedEffect(uiState.value.currentWeather) {
        uiState.value.currentWeather?.let { current ->
            onDayNightDetermined(current.isDay)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when {
            uiState.value.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.value.errorMessage.isNotEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${uiState.value.errorMessage}")
                }
            }

            uiState.value.currentWeather != null && uiState.value.dailyWeather != null -> {
                uiState.value.hourlyWeather?.let {
                    WeatherContent(
                        hourlyWeather = it,
                        city = uiState.value.city,
                        dailyWeather = uiState.value.dailyWeather!!,
                        currentWeather = uiState.value.currentWeather!!,
                    )
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No data available")
                }
            }
        }
    }
}

@Composable
fun WeatherContent(
    hourlyWeather: HourlyWeather,
    currentWeather: CurrentWeather,
    city: String,
    dailyWeather: DailyWeatherResponse,

    ) {
    LazyColumn {
        item {
            CollapsingWeatherHeader(
                currentWeather.isDay,
                hourlyWeather = hourlyWeather,
                city = city
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    WeatherMetricCard(
                        iconRes = R.drawable.fast_wind,
                        value = "${currentWeather.windSpeed} KM/h",
                        label = "Wind",
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMetricCard(
                        iconRes = R.drawable.humidity,
                        value = "${currentWeather.humidity}%",
                        label = "Humidity",
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMetricCard(
                        iconRes = R.drawable.rain,
                        value = "${currentWeather.rain} mm",
                        label = "Rain",
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    WeatherMetricCard(
                        iconRes = R.drawable.uv_02,
                        value = "${hourlyWeather.hourly.uvIndex[0]}",
                        label = "UV Index",
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMetricCard(
                        iconRes = R.drawable.arrow_down_05,
                        value = "${currentWeather.surfacePressure} hPa",
                        label = "Pressure",
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMetricCard(
                        iconRes = R.drawable.temperature,
                        value = "${currentWeather.apparentTemperature}°C",
                        label = "Feels Like",
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor =  MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        item {
            Text(
                text = "Today",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, start = 12.dp)
            )
        }

        item { HourlyWeatherRow(hourlyWeather = hourlyWeather,currentWeather.isDay) }

        item {
            Text(
                text = "Next 7 Days",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, start = 12.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            WeeklyForecastCard(dailyWeather = dailyWeather, isDay = currentWeather.isDay)
        }
    }
}

@Composable
fun CollapsingWeatherHeader(
     isDay: Boolean,
    hourlyWeather: HourlyWeather,
    city: String
) {
    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00"))
    val currentIndex = hourlyWeather.hourly.time.indexOfFirst { it == currentTime }

    val temperature = hourlyWeather.hourly.temperature2m.getOrElse(currentIndex) { "--" }
    val weatherCode = hourlyWeather.hourly.weatherCode.getOrElse(currentIndex) { 0 }
    val weatherIcon = getWeatherIcon(weatherCode,isDay)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
                contentDescription = "Location Icon",
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

        Image(
            painter = painterResource(weatherIcon),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(DpSize(220.dp, 200.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$temperature°C",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = getWeatherCondition(weatherCode),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        val maxTemperature = hourlyWeather.hourly.temperature2m.maxOrNull()?.toString() ?: "--"
        val minTemperature = hourlyWeather.hourly.temperature2m.minOrNull()?.toString() ?: "--"

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
                    shape = RoundedCornerShape(100.dp)
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 24.dp, end = 24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_up_04),
                    contentDescription = "High Temperature",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onBackground

                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "$maxTemperature°C",
                    style = MaterialTheme.typography.bodyMedium
                    ,color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.24f))
                        .width(1.dp)
                        .height(14.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    painter = painterResource(id = R.drawable.arrow_down_04),
                    contentDescription = "Low Temperature",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onBackground

                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "$minTemperature°C",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
fun WeeklyForecastCard(dailyWeather: DailyWeatherResponse, isDay: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 32.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f), RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
    ) {
        dailyWeather.daily.time.take(7).forEachIndexed { index, date ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .width(120.dp)
                        .padding(top = 22.dp, bottom = 21.dp, end = 9.5.dp, start = 16.dp),
                    text = getDayName(date),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                Image(
                    painter = painterResource(getWeatherIcon(dailyWeather.daily.weatherCode[index],isDay)),
                    contentDescription = "Weather Icon",
                    modifier = Modifier
                        .size(91.dp, 45.dp)
                        .padding(end = 9.5.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_up_04),
                        contentDescription = "High Temperature",
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f)

                    )

                    Spacer(modifier = Modifier.width(2.5.dp))


                    Text(
                        text = "${dailyWeather.daily.temperature2mMax[index]}°C",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.24f))
                            .width(1.dp)
                            .height(14.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.arrow_down_04),
                        contentDescription = "Low Temperature",
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${dailyWeather.daily.temperature2mMin[index]}°C",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        modifier = Modifier.padding(end = 12.dp)

                    )
                }

            }

            if (index < 6) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f)
                )
            }
        }
    }
}

@Composable
fun HourlyWeatherRow(hourlyWeather: HourlyWeather,isDay: Boolean) {
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
                        .border(1.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f)
                            , RoundedCornerShape(20.dp))
                        .background( MaterialTheme.colorScheme.background.copy(alpha = 0.7f)),
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
                    painter = painterResource(getWeatherIcon(weatherCode, isDay =isDay )),
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

fun getDayName(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(date, formatter)
    return localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

fun getWeatherCondition(weatherCode: Int): String {
    return when (weatherCode) {
        0 -> "Clear Sky"
        1 -> "Mainly Clear"
        2 -> "Partly Cloudy"
        3 -> "Overcast"
        45 -> "Fog"
        48 -> "Depositing Rime Fog"
        51 -> "Light Drizzle"
        53 -> "Moderate Drizzle"
        55 -> "Dense Drizzle"
        61 -> "Light Rain"
        63 -> "Moderate Rain"
        65 -> "Heavy Rain"
        71 -> "Light Snowfall"
        73 -> "Moderate Snowfall"
        75 -> "Heavy Snowfall"
        80 -> "Rain Showers (Slight)"
        81 -> "Rain Showers (Moderate)"
        82 -> "Rain Showers (Heavy)"
        85 -> "Snow Showers (Slight)"
        86 -> "Snow Showers (Heavy)"
        95 -> "Thunderstorms (Slight)"
        96 -> "Thunderstorms (Moderate)"
        99 -> "Thunderstorms with Hail"
        else -> "Weather Not Recognized"
    }
}

fun getWeatherIcon(weatherCode: Int, isDay: Boolean): Int {
    val dayWeatherIcons = mapOf(
        0 to R.drawable.clear_sky,
        1 to R.drawable.mainly_clear,
        2 to R.drawable.partly_cloudy,
        3 to R.drawable.overcast,
        45 to R.drawable.fog,
        48 to R.drawable.depositing_rime_fog,
        51 to R.drawable.drizzle_light,
        53 to R.drawable.drizzle_moderate,
        55 to R.drawable.drizzle_intensity,
        61 to R.drawable.rain_slight,
        63 to R.drawable.rain_moderate,
        65 to R.drawable.rain_intensity,
        71 to R.drawable.snow_fall_light,
        73 to R.drawable.snow_fall_moderate,
        75 to R.drawable.snow_fall_intensity,
        80 to R.drawable.snow_shower_slight,
        81 to R.drawable.rain_shower_moderate,
        82 to R.drawable.rain_shower_violent,
        85 to R.drawable.rain_shower_slight,
        86 to R.drawable.snow_shower_heavy,
        95 to R.drawable.thunder_storm_slight_or_moderate,
        96 to R.drawable.thunder_storm_with_slight_hail,
        99 to R.drawable.thunder_storm_with_heavy_hail
    )

    val nightWeatherIcons = mapOf(
        0 to R.drawable.clear_sky_night,
        1 to R.drawable.mainly_clear_night,
        2 to R.drawable.partly_cloudy_night,
        3 to R.drawable.overcast_night,
        45 to R.drawable.fog_night,
        48 to R.drawable.depositing_rime_night,
        51 to R.drawable.drizzle_light_night,
        53 to R.drawable.drizzle_moderate_night,
        55 to R.drawable.drizzle_intensity_night,
        61 to R.drawable.rain_slight_night,
        63 to R.drawable.rain_moderate_night,
        65 to R.drawable.rain_intensity_night,
        71 to R.drawable.snow_fall_light_night,
        73 to R.drawable.snow_fall_moderate_night,
        75 to R.drawable.snow_fall_intensity_night,
        80 to R.drawable.rain_shower_slight_night,
        81 to R.drawable.rain_shower_moderate_night,
        82 to R.drawable.rain_shower_violent_night,
        85 to R.drawable.snow_shower_slight_night,
        86 to R.drawable.snow_shower_heavy_night,
        95 to R.drawable.thunderstrom_slight_or_moderate_night,
        96 to R.drawable.thunderstrom_with_heavy_hail_night,
        99 to R.drawable.thunderstrom_with_slight_hail_night
    )

    return if (isDay) {
        dayWeatherIcons.getOrElse(weatherCode) { R.drawable.clear_sky }
    } else {
        nightWeatherIcons.getOrElse(weatherCode) { R.drawable.clear_sky_night }
    }
}

