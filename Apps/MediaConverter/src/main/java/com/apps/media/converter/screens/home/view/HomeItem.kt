package com.apps.media.converter.screens.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import modules.core.theme.greyText

@Composable
fun HomeItem(
    modifier: Modifier = Modifier,
    itemText: String = "Cut Audio",
    onClick: () -> Unit = {},
//    @DrawableRes itemIcon: Int = R.drawable.ic_cut_audio,
) {
    Box(
        modifier = modifier
            .background(
                color = Color.White, shape = MaterialTheme.shapes.medium,
            )
            .clickable(
                onClick = onClick,
                role = Role.Button,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            )
            .padding(
                vertical = 24.dp,
            )
            .fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
//            Image(
//                modifier = Modifier.padding(12.0.dp),
//                painter = painterResource(id = itemIcon),
//                contentDescription = itemText,
//            )

            Text(
                text = itemText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = greyText,
                ),
            )
        }
    }
}

@Preview
@Composable
fun HomeItemPreview() {
    HomeItem()
}