package com.weather_app

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.weather_app.android.theme.LocalGradientTheme
import com.weather_app.android.theme.Weather_appTheme
import com.weather_app.ui.screens.WeatherScreen

class MainActivity : ComponentActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    private var permissionsGranted by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            permissionsGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        }

        checkAndRequestPermissions()

        setContent {
            var isDayTime by rememberSaveable { mutableStateOf(true) }

            Weather_appTheme(!isDayTime) {
                Scaffold { innerPadding ->
                    GradientBackground {
                        if (permissionsGranted) {
                            WeatherScreen(
                                paddingValues = innerPadding,
                                onDayNightDetermined = { isDay ->
                                    isDayTime = isDay
                                }
                            )
                        } else {
                            PermissionRequestUI {
                                requestPermissionLauncher.launch(
                                    arrayOf(
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkAndRequestPermissions() {
        val fineLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        )

        permissionsGranted = fineLocationPermission == PackageManager.PERMISSION_GRANTED ||
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED

        if (!permissionsGranted) {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}

@Composable
fun PermissionRequestUI(onRequestPermission: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Location permissions are required to fetch weather data.")
        Spacer(modifier = Modifier.height(8.dp))
        androidx.compose.material3.Button(onClick = onRequestPermission) {
            Text("Grant Permissions")
        }
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