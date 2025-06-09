package com.weather_app.android.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush

data class GradientTheme(
    val backgroundGradient: Brush
)

val LocalGradientTheme = staticCompositionLocalOf {
    GradientTheme(
        backgroundGradient = Brush.linearGradient(
            colors = listOf(DarkNavy, DarkPurple)
        )
    )
}