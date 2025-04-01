package jc.app.bank_pick.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jc.app.bank_pick.screen.main.MainScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            MainScreen()
        }
    }
}