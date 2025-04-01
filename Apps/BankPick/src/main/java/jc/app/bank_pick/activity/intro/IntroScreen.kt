package jc.app.bank_pick.activity.intro

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun IntroScreen(
    nextScreen: () -> Unit = {},
) {
    val pages = listOf(
        OnBoardingPage.First, OnBoardingPage.Second, OnBoardingPage.Third
    )
    val pagerState = rememberPagerState(
        pageCount = {
            pages.size
        },
    )

    var currentIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            Timber.tag("PageChange").d("Page changed to $page")
        }
    }

    LaunchedEffect(currentIndex) {
        pagerState.animateScrollToPage(currentIndex)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.weight(1F))

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            userScrollEnabled = false,
        ) { position ->
            PagerScreen(onBoardingPage = pages[position])
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color by animateColorAsState(
                    targetValue = if (pagerState.currentPage == iteration) Color(0xFF0066FF)
                    else Color(0x330066FF), animationSpec = tween(durationMillis = 300)
                )

                val width by animateDpAsState(
                    targetValue = if (pagerState.currentPage == iteration) 16.dp else 6.dp,
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(width, 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1F))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = {
                if (currentIndex < pages.size - 1) {
                    currentIndex += 1
                } else {
                    nextScreen.invoke()
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = Color(0xFF0066FF)
            ),
        ) {
            Text(
                text = "Next", style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    fontWeight = FontWeight.W500,
                    fontSize = TextUnit(16F, TextUnitType.Sp),
                    lineHeight = TextUnit(24F, TextUnitType.Sp),
                    textAlign = TextAlign.Center,
                )
            )
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 12.dp, end = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(1F),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Pager Image"
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(onBoardingPage.title),
            minLines = 2,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color(0xFF1E1E2D),
                fontWeight = FontWeight.SemiBold,
                fontSize = TextUnit(26F, TextUnitType.Sp),
                textAlign = TextAlign.Center,
            ),
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .padding(top = 20.dp),
            text = stringResource(onBoardingPage.description),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF7E848D),
                fontWeight = FontWeight.Medium,
                fontSize = TextUnit(14F, TextUnitType.Sp),
                textAlign = TextAlign.Center,
            ),
        )
    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.First)
    }
}

@Composable
@Preview(showBackground = true)
fun SecondOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Second)
    }
}

@Composable
@Preview(showBackground = true)
fun ThirdOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(onBoardingPage = OnBoardingPage.Third)
    }
}