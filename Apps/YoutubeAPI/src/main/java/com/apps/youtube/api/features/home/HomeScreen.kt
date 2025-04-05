package com.apps.youtube.api.features.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.apps.youtube.api.features.home.widget.CategoriesLine
import com.apps.youtube.api.features.home.widget.HomeHeader
import timber.log.Timber

@PreviewLightDark()
@Composable
fun HomeScreen() {
    val isDarkTheme = isSystemInDarkTheme()

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .background(
                    if (isDarkTheme) Color.Black else Color.White
                )
                .fillMaxSize(),
        ) {
            HomeHeader()

            CategoriesLine(
                openDrawer = {
                    Timber.tag("HomeScreen").d("Open Drawer")
                })
        }
    }
}