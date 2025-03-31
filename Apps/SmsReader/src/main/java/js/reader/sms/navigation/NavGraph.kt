package js.reader.sms.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import js.reader.sms.screen.MainScreen
import js.reader.sms.screen.PermissionScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Permission.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.Permission.route) {
            PermissionScreen(
                onPermissionGranted = {
                    navController.popBackStack()
                    navController.navigate(Screen.Main.route)
                })
        }
        composable(route = Screen.Main.route) {
            MainScreen()
        }
    }
}