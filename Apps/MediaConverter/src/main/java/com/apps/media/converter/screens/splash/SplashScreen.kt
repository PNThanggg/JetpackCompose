package com.apps.media.converter.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.apps.media.converter.AppScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController? = null
) {
    LaunchedEffect(Unit) {
        delay(1500L)
        navController?.navigate(AppScreens.Home.route) {
            popUpTo(AppScreens.Splash.route) { inclusive = true }
        }
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Splash Screen")
        }
    }
}