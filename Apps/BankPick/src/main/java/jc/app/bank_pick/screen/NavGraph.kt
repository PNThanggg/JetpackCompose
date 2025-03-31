package jc.app.bank_pick.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jc.app.bank_pick.screen.main.MainScreen
import jc.app.bank_pick.screen.splash.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController,
            )
        }

        composable(Screen.Home.route) {
            MainScreen()
        }
    }
}