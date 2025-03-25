package com.apps.spotify.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apps.spotify.MainViewModel
import com.apps.spotify.features.home.HomeScreen
import com.apps.spotify.features.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val mainViewModel = viewModel<MainViewModel>()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(
            Screen.Splash.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700)
                )
            },
        ) {
            SplashScreen(
                navController = navController,
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                mainViewModel = mainViewModel,
            )
        }
    }
}