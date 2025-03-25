package com.apps.spotify.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.apps.spotify.MainViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
) {
    LaunchedEffect(Unit) {
        mainViewModel.getApiToken()
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
            Text(
                text = "Home Screen",
            )
        }
    }
}