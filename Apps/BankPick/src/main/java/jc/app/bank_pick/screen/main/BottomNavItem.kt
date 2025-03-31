package jc.app.bank_pick.screen.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import jc.app.bank_pick.R
import jc.app.bank_pick.screen.Screen

sealed class BottomNavItem(
    val route: String, @DrawableRes val icon: Int, @StringRes val label: Int
) {
    object Home : BottomNavItem(Screen.Home.route, R.drawable.ic_home, R.string.home)

    object MyCards : BottomNavItem(Screen.MyCard.route, R.drawable.ic_my_card, R.string.my_cards)

    object Statistics :
        BottomNavItem(Screen.Home.route, R.drawable.ic_statistics, R.string.statistics)

    object Settings : BottomNavItem(Screen.Home.route, R.drawable.ic_settings, R.string.settings)
}