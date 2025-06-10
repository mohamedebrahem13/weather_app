package com.weather_app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weather_app.R
import com.weather_app.domain.models.CurrentWeather
import com.weather_app.domain.models.DailyWeatherResponse
import com.weather_app.domain.models.HourlyWeather
import com.weather_app.ui.composable.HourlyWeatherRow
import com.weather_app.ui.composable.WeatherHeaderContainer
import com.weather_app.ui.composable.WeatherMetricsSection
import com.weather_app.ui.composable.WeeklyForecastCard
import com.weather_app.ui.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel


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
                        viewModel = viewModel
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
    viewModel: WeatherViewModel
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
                scrollOffset,
                viewModel
            )
        }
        item {
            WeatherMetricsSection(currentWeather,hourlyWeather)
        }
        item {
            Text(
                text = stringResource(R.string.today),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 24.dp, start = 12.dp)
            )
        }

        item { HourlyWeatherRow(hourlyWeather = hourlyWeather, currentWeather.isDay, viewModel) }

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
            WeeklyForecastCard(viewModel = viewModel, dailyWeather = dailyWeather)
        }
    }
}

