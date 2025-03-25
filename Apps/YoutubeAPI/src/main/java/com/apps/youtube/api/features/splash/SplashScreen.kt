package com.apps.youtube.api.features.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apps.youtube.api.R
import com.apps.youtube.api.navigate.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
) {
    LaunchedEffect(Unit) {
        delay(500)

        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .background(
                    color = Color.White
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.width(240.dp).height(240.dp),
                painter = painterResource(id = R.drawable.ic_logo_splash),
                contentDescription = stringResource(id = R.string.app_name)
            )
        }
    }
}