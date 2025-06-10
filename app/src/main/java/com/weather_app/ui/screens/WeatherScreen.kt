package com.weather_app.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
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
                    Text(text = stringResource(R.string.no_data_available))
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
    val scrollState = rememberLazyListState()
    val scrollOffset by remember {
        derivedStateOf {
            minOf(1f, scrollState.firstVisibleItemScrollOffset / 300f)
        }
    }
    LazyColumn(state = scrollState) {
        item {
            WeatherHeaderContainer(
                currentWeather.isDay,
                hourlyWeather = hourlyWeather,
                city = city,
                scrollOffset
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
                        label = stringResource(R.string.wind),
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMetricCard(
                        iconRes = R.drawable.humidity,
                        value = "${currentWeather.humidity}%",
                        label = stringResource(R.string.humidity),
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMetricCard(
                        iconRes = R.drawable.rain,
                        value = "${currentWeather.rain}%",
                        label = stringResource(R.string.rain),
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
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
                        label = stringResource(R.string.uv_index),
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMetricCard(
                        iconRes = R.drawable.arrow_down_05,
                        value = "${currentWeather.surfacePressure} hPa",
                        label = stringResource(R.string.pressure),
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )

                    WeatherMetricCard(
                        iconRes = R.drawable.temperature,
                        value = "${currentWeather.apparentTemperature}°C",
                        label = stringResource(R.string.feels_like),
                        iconTint = LightBlue,
                        valueTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f),
                        labelTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        backgroundColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.7f),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        item {
            Text(
                text = stringResource(R.string.today),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, start = 12.dp)
            )
        }

        item { HourlyWeatherRow(hourlyWeather = hourlyWeather, currentWeather.isDay) }

        item {
            Text(
                text = stringResource(R.string.next_7_days),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, start = 12.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))
            WeeklyForecastCard(dailyWeather = dailyWeather)
        }
    }
}
@Composable
fun WeatherHeaderContainer(
    isDay: Boolean,
    hourlyWeather: HourlyWeather,
    city: String,
    scrollOffset: Float
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

        CollapsingWeatherContent(
            isDay = isDay,
            hourlyWeather = hourlyWeather,
            scrollOffset = scrollOffset
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CollapsingWeatherContent(
    isDay: Boolean,
    hourlyWeather: HourlyWeather,
    scrollOffset: Float
) {
    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00"))
    val currentIndex = hourlyWeather.hourly.time.indexOfFirst { it == currentTime }

    val temperature = hourlyWeather.hourly.temperature2m.getOrElse(currentIndex) { "--" }
    val weatherCode = hourlyWeather.hourly.weatherCode.getOrElse(currentIndex) { 0 }
    val weatherIcon = getWeatherIcon(weatherCode, isDay)
    val maxTemperature = hourlyWeather.hourly.temperature2m.maxOrNull()?.toString() ?: "--"
    val minTemperature = hourlyWeather.hourly.temperature2m.minOrNull()?.toString() ?: "--"

    val clampedOffset = scrollOffset.coerceIn(0f, 1f)

    val iconWidth by animateDpAsState(
        targetValue = lerp(220.dp, 124.dp, clampedOffset),
        animationSpec = tween(durationMillis = 400),
        label = "Animated Icon Width"
    )
    val iconHeight by animateDpAsState(
        targetValue = lerp(200.dp, 112.dp, clampedOffset),
        animationSpec = tween(durationMillis = 400),
        label = "Animated Icon Height"
    )

    Spacer(modifier = Modifier.height(8.dp))

    AnimatedContent(
        targetState = clampedOffset < 0.1f,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "CollapseSwitch"
    ) { isExpanded ->
        if (isExpanded) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(weatherIcon),
                    contentDescription = stringResource(R.string.weather_icon),
                    modifier = Modifier.size(iconWidth, iconHeight),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
                WeatherTextBlock(
                    temperature = temperature.toString(),
                    weatherCode = weatherCode,
                    maxTemperature = maxTemperature,
                    minTemperature = minTemperature
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(weatherIcon),
                    contentDescription = stringResource(R.string.weather_icon),
                    modifier = Modifier.size(iconWidth, iconHeight),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                WeatherTextBlock(
                    temperature = temperature.toString(),
                    weatherCode = weatherCode,
                    maxTemperature = maxTemperature,
                    minTemperature = minTemperature,
                    alignStart = false
                )
            }
        }
    }
}
@Composable
private fun WeatherTextBlock(
    temperature: String,
    weatherCode: Int,
    maxTemperature: String,
    minTemperature: String,
    alignStart: Boolean = false
) {
    Column(
        horizontalAlignment = if (alignStart) Alignment.Start else Alignment.CenterHorizontally
    ) {
        Text(
            text = "$temperature°C",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = getWeatherCondition(weatherCode),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

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
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_up_04),
                    contentDescription = stringResource(R.string.high_temperature),
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "$maxTemperature°C",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
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
                    contentDescription = stringResource(R.string.low_temperature),
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "$minTemperature°C",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
@Composable
fun WeeklyForecastCard(dailyWeather: DailyWeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 32.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.08f),
                RoundedCornerShape(24.dp)
            )
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
                    painter = painterResource(getDayWeatherIcon(dailyWeather.daily.weatherCode[index])),
                    contentDescription = stringResource(R.string.weather_icon),
                    modifier = Modifier
                        .size(91.dp, 45.dp)
                        .padding(end = 9.5.dp, bottom = 8.dp, top = 8.dp)

                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_up_04),
                        contentDescription = stringResource(R.string.high_temperature),
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

fun getDayName(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val localDate = LocalDate.parse(date, formatter)
    return localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

@Composable
fun getWeatherCondition(weatherCode: Int): String {
    val context = LocalContext.current

    return when (weatherCode) {
        0 -> context.getString(R.string.clear_sky)
        1 -> stringResource(R.string.mainly_clear)
        2 -> stringResource(R.string.partly_cloudy)
        3 -> stringResource(R.string.overcast)
        45 -> stringResource(R.string.fog)
        48 -> stringResource(R.string.depositing_rime_fog)
        51 -> stringResource(R.string.light_drizzle)
        53 -> stringResource(R.string.moderate_drizzle)
        55 -> stringResource(R.string.dense_drizzle)
        61 -> stringResource(R.string.light_rain)
        63 -> stringResource(R.string.moderate_rain)
        65 -> stringResource(R.string.heavy_rain)
        71 -> stringResource(R.string.light_snowfall)
        73 -> stringResource(R.string.moderate_snowfall)
        75 -> stringResource(R.string.heavy_snowfall)
        80 -> stringResource(R.string.rain_showers_slight)
        81 -> stringResource(R.string.rain_showers_moderate)
        82 -> stringResource(R.string.rain_showers_heavy)
        85 -> stringResource(R.string.snow_showers_slight)
        86 -> stringResource(R.string.snow_showers_heavy)
        95 -> stringResource(R.string.thunderstorms_slight)
        96 -> stringResource(R.string.thunderstorms_moderate)
        99 -> stringResource(R.string.thunderstorms_with_hail)
        else -> stringResource(R.string.weather_not_recognized)
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

fun getDayWeatherIcon(weatherCode: Int): Int {
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

    return dayWeatherIcons.getOrElse(weatherCode) { R.drawable.clear_sky }
}

