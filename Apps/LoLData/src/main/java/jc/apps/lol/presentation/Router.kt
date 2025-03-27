package jc.apps.lol.presentation

sealed class AppRouter(val route: String) {
    object Splash : AppRouter("SplashScreen")

    object Home : AppRouter("Home")

    object Detail : AppRouter("Detail/{name}") {
        fun createRoute(name: String) = "Detail/$name"
    }
}