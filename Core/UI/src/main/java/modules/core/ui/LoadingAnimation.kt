package modules.core.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun LoadingAnimation(
    modifier: Modifier = Modifier,
    circleSize: Dp = 24.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    spaceBetween: Dp = 10.dp,
    travelDistance: Dp = 20.dp,
) {
    val circles = listOf(
        remember {
            Animatable(initialValue = 0F)
        },
        remember {
            Animatable(initialValue = 0F)
        },
        remember {
            Animatable(initialValue = 0F)
        },
    )
    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)

            animatable.animateTo(
                targetValue = 1F,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0F at 0 using LinearOutSlowInEasing
                        1.0F at 300 using LinearOutSlowInEasing
                        0.0F at 600 using LinearOutSlowInEasing
                        0.0F at 1200 using LinearOutSlowInEasing
                    },
                ),
            )
        }
    }

    val circleValues = circles.map {
        it.value
    }
    val distance = with(LocalDensity.current) {
        travelDistance.toPx()
    }

    val lastCircle = circleValues.size - 1

    Row(
        modifier = modifier,
    ) {
        circleValues.forEachIndexed { index, value ->
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = circleColor, shape = CircleShape,
                    ),
            )

            if (index != lastCircle) {
                Spacer(modifier = Modifier.size(spaceBetween))
            }
        }
    }
}