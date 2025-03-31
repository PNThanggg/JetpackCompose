package jc.app.bank_pick.screen.intro

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onClick: () -> Unit = {}
) {
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
//    val pagerState = rememberPagerState()
}