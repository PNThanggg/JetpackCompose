package com.apps.youtube.api.features.home.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.apps.youtube.api.R

@Preview(
    showBackground = true
)
@PreviewLightDark()
@Composable
fun HomeHeader(
    castVideo: () -> Unit = {},
    notification: () -> Unit = {},
    search: () -> Unit = {}
) {
    val isDarkTheme = isSystemInDarkTheme()
    val imageRes = if (isDarkTheme) R.drawable.yt_logo_dark else R.drawable.yt_logo_white

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp, horizontal = 12.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier.height(20.dp)
        )

        Row {
            IconButton(onClick = castVideo) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cast_video),
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    contentDescription = "Cast Video",
                )
            }

            IconButton(onClick = notification) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    contentDescription = "Cast Video",
                )
            }

            IconButton(onClick = search) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    contentDescription = "Search",
                )
            }
        }
    }
}