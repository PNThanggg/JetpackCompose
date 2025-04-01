package jc.app.bank_pick.activity.main

sealed class Screen(
    val route: String,
) {
    object Home : Screen("home_screen")

    object Settings : Screen("settings_screen")

    object MyCard : Screen("my_card_screen")

    object Statistics : Screen("statistics_screen")
}