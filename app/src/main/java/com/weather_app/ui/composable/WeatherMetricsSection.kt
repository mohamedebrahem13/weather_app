package com.weather_app.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weather_app.R
import com.weather_app.android.theme.LightBlue
import com.weather_app.domain.models.CurrentWeather
import com.weather_app.domain.models.HourlyWeather

@Composable
fun WeatherMetricsSection(
    currentWeather: CurrentWeather,
    hourlyWeather: HourlyWeather
) {
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