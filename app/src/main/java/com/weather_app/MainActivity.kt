package com.weather_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.weather_app.ui.theme.LocalGradientTheme
import com.weather_app.ui.theme.Weather_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Weather_appTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GradientBackground {
                        Greeting(
                            name = "John",
                            res = R.drawable.clear_sky,
                            paddingValues = innerPadding
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, @DrawableRes res: Int,paddingValues: PaddingValues) {
    Column(modifier = modifier.padding(paddingValues)) {
        Text(text = "Hello $name!")
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = res),
            contentDescription = "Greeting image",
            contentScale = ContentScale.Fit
        )
    }
}
@Composable
fun GradientBackground(content: @Composable () -> Unit) {
    val gradient = LocalGradientTheme.current.backgroundGradient

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradient)
    ) {
        content()
    }
}