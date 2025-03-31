package jc.app.bank_pick.screen.intro

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import jc.app.bank_pick.R

sealed class OnBoardingPage(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int,
) {
    object First : OnBoardingPage(
        image = R.drawable.intro_1,
        title = R.string.intro_title_1,
        description = R.string.intro_content_1,
    )

    object Second : OnBoardingPage(
        image = R.drawable.intro_2,
        title = R.string.intro_title_2,
        description = R.string.intro_content_2,
    )

    object Third : OnBoardingPage(
        image = R.drawable.intro_3,
        title = R.string.intro_title_3,
        description = R.string.intro_content_3,
    )
}