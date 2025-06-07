package com.weather_app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.weather_app.R

val Urbanist = FontFamily(
    Font(R.font.urbanist, FontWeight.Normal),
    Font(R.font.urbanist_medium, FontWeight.Medium),
    Font(R.font.urbanist_semibold, FontWeight.SemiBold),
    Font(R.font.urbanist_bold, FontWeight.Bold)
)

val Typography = Typography(
    // Regular 14sp
    bodySmall = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),

    // Medium 16sp
    bodyMedium = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),

    // Medium 20sp
    titleMedium = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),

    // SemiBold 20sp
    titleLarge = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),

    // SemiBold 64sp
    displayLarge = TextStyle(
        fontFamily = Urbanist,
        fontWeight = FontWeight.SemiBold,
        fontSize = 64.sp
    )
)