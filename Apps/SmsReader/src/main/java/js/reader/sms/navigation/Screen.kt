package js.reader.sms.navigation

sealed class Screen(
    val route: String,
) {
    object Permission : Screen("permission_screen")

    object Main : Screen("main_screen")
}