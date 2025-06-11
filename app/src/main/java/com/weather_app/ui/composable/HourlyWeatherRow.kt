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

@Composable
fun HourlyWeatherRow(
    hours: List<String>,
    temperatures: List<String>,
    weatherIconResIds: List<Int>
) {
    val itemCount = listOf(hours.size, temperatures.size, weatherIconResIds.size).min()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(itemCount) { index ->
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
                        text = "${temperatures[index]}°C",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.87f)
                    )
                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = hours[index],
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                Image(
                    painter = painterResource(id = weatherIconResIds[index]),
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