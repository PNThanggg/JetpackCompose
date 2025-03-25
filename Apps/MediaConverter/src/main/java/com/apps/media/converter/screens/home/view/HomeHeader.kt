package com.apps.media.converter.screens.home.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.apps.media.converter.R

@Composable
fun HomeHeader(
    onSettingClick: () -> Unit = {},
) {
    val density = LocalDensity.current
    val statusBarHeightPx = with(density) {
        WindowInsets.statusBars.asPaddingValues().calculateTopPadding().toPx()
    }
    val statusBarHeightDp = with(density) { statusBarHeightPx.toDp() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = statusBarHeightDp, bottom = 0.dp, end = 0.dp, start = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.txt_mv_editor).uppercase(),
            style = MaterialTheme.typography.headlineSmall.copy(
                color = Color.White
            )
        )

        IconButton(onClick = {
            onSettingClick.invoke()
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_setting),
                contentDescription = "SettingButton",
            )
        }
    }
}
