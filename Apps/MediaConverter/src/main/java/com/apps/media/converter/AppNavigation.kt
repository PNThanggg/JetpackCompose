package com.apps.media.converter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apps.media.converter.screens.home.HomeScreen
import com.apps.media.converter.screens.setting.SettingScreen
import com.apps.media.converter.screens.splash.SplashScreen
import timber.log.Timber

sealed class AppScreens(
    val route: String,
) {
    data object Splash : AppScreens("splash_screen")
    data object Intro : AppScreens("intro_screen")
    data object Home : AppScreens("home_screen")
    data object Setting : AppScreens("setting_screen")
}


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = AppScreens.Home.route, modifier = modifier
    ) {
        composable(AppScreens.Splash.route) {
            SplashScreen(
                navController = navController,
            )
        }

        composable(AppScreens.Home.route) {
            HomeScreen(
                navController = navController,
            )
        }

        composable(AppScreens.Setting.route) {
            SettingScreen()
        }
    }
}