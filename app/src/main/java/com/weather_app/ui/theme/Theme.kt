package com.weather_app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    background = Color.White,
    primary = LightBlue,
    onBackground = DarkNavy,
)

val DarkColorScheme = darkColorScheme(
    background = DarkNavy,
    primary = DarkPurple,
    onBackground = PureWhite,
)
@Composable
fun Weather_appTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val gradientTheme = GradientTheme(
        backgroundGradient = if (!darkTheme) {
            Brush.linearGradient(
                colors = listOf(LightBlue, PureWhite),
                start = Offset(0f, 0f),
                end = Offset(0f, 1000f)
            )
        } else {
            Brush.linearGradient(
                colors = listOf(DarkNavy, DarkPurple),
                start = Offset(0f, 0f),
                end = Offset(0f, 1000f)
            )
        }
    )

    CompositionLocalProvider(LocalGradientTheme provides gradientTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}