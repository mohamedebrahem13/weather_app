package com.weather_app.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.weather_app.R
import com.weather_app.domain.models.DailyWeatherResponse
import com.weather_app.ui.viewmodel.WeatherViewModel

@Composable
fun WeeklyForecastCard(
    dailyWeather: DailyWeatherResponse, viewModel: WeatherViewModel
) {
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
                    text = viewModel.getDayName(date),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )

                Image(
                    painter = painterResource(viewModel.getDayWeatherIcon(dailyWeather.daily.weatherCode[index])),
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
