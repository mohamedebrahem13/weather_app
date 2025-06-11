package com.weather_app.ui.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.weather_app.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CollapsingWeatherContent(
    temperature: String,
    weatherIcon: Int,
    maxTemperature: String,
    minTemperature: String,
    scrollOffset: Float,
    weatherCondition: String
) {
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
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(iconWidth + 20.dp, iconHeight + 20.dp)
                            .shadow(100.dp, shape = CircleShape)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.01f))
                    )

                    Image(
                        painter = painterResource(weatherIcon),
                        contentDescription = stringResource(R.string.weather_icon),
                        modifier = Modifier.size(iconWidth, iconHeight)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                WeatherTextBlock(
                    temperature = temperature,
                    maxTemperature = maxTemperature,
                    minTemperature = minTemperature,
                    condition = weatherCondition
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
                Box(contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(iconWidth + 20.dp, iconHeight + 20.dp)
                            .shadow(100.dp, shape = CircleShape)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.01f))
                    )

                    Image(
                        painter = painterResource(weatherIcon),
                        contentDescription = stringResource(R.string.weather_icon),
                        modifier = Modifier.size(iconWidth, iconHeight)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                WeatherTextBlock(
                    temperature = temperature,
                    maxTemperature = maxTemperature,
                    minTemperature = minTemperature,
                    alignStart = false,
                    condition = weatherCondition
                )
            }
        }
    }
}


@Composable
private fun WeatherTextBlock(
    temperature: String,
    maxTemperature: String,
    minTemperature: String,
    condition: String,
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
            text = condition,
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
