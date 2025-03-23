package jc.apps.task_manager.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jc.apps.task_manager.ui.home.HomeScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME_ROUTE,
        modifier = modifier
    ) {
        composable(
            route = AppDestinations.HOME_ROUTE,
        ) { navBackStackEntry ->
            HomeScreen()
        }
    }
}