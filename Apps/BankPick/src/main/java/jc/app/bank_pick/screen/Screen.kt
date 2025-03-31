package jc.app.bank_pick.screen

sealed class Screen(
    val route: String,
) {
    object Splash : Screen("splash_screen")

    object Intro : Screen("intro_screen")

    object Login : Screen("login_screen")

    object Register : Screen("register_screen")

    object Home : Screen("home_screen")

    object Settings : Screen("settings_screen")

    object MyCard : Screen("my_card_screen")

    object Statistics : Screen("statistics_screen")
}