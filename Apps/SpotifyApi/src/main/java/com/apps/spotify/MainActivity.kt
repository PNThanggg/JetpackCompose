package com.apps.spotify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.apps.spotify.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import modules.core.theme.JetpackComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        setContent {
            JetpackComposeTheme {
                AppNavigation()
            }
        }
    }
}
