package com.apps.spotify.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")

    data object Home : Screen("home")
}