package com.apps.youtube.api.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBars
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

    val insets = WindowInsets.statusBars.asPaddingValues()
    val statusBarHeight = insets.calculateTopPadding()

    Column(
        modifier = Modifier
            .background(
                if (isDarkTheme) Color.Black else Color.White
            )
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(statusBarHeight))

        HomeHeader()

        CategoriesLine(
            openDrawer = {
                Timber.tag("HomeScreen").d("Open Drawer")
            },
        )
    }
}