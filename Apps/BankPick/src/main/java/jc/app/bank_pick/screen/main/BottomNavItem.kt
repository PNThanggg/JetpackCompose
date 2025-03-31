package jc.app.bank_pick.screen.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import jc.app.bank_pick.R
import jc.app.bank_pick.screen.Screen

sealed class BottomNavItem(
    val route: String, @DrawableRes val icon: Int, @StringRes val label: Int
) {
    object Home : BottomNavItem(Screen.Home.route, R.drawable.img_avatar, R.string.home)
}