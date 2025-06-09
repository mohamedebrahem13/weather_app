package com.weather_app.android.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun SetStatusBarColor() {
    val view = LocalView.current
    if (!view.isInEditMode) {
        val window = (view.context as Activity).window


        SideEffect {
            // Force white icons by setting isAppearanceLightStatusBars to false
            WindowInsetsControllerCompat(window, view).isAppearanceLightStatusBars = false
        }
    }
}