package com.apps.media.converter.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apps.media.converter.AppScreens
import com.apps.media.converter.R
import com.apps.media.converter.screens.home.view.HomeHeader
import com.apps.media.converter.screens.home.view.HomeItem
import modules.core.theme.endGradientColor
import modules.core.theme.startGradientColor

@Composable
fun HomeScreen(
    navController: NavController? = null
) {
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier.background(
                brush = Brush.linearGradient(
                    colors = listOf(startGradientColor, endGradientColor),
                    start = Offset(0f, 0f),
                    end = Offset(
                        Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY
                    )
                ),
                shape = RoundedCornerShape(
                    bottomEnd = 12.0.dp,
                    bottomStart = 12.0.dp,
                ),
            ),
        ) {
            Column {
                HomeHeader(onSettingClick = {
                    navController?.navigate(AppScreens.Setting.route)
                })

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HomeItem(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        itemText = stringResource(R.string.cut_audio),
                        onClick = {

                        },
                    )

                    HomeItem(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        itemText = stringResource(R.string.cut_audio),
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HomeItem(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        itemText = stringResource(R.string.merge_audio),
                    )

                    HomeItem(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp),
                        itemText = stringResource(R.string.merge_video),
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .padding(top = 6.dp, bottom = 20.dp)
                        .background(
                            color = Color.White, shape = MaterialTheme.shapes.small,
                        )
                        .clickable(
                            onClick = {},
                            role = Role.Button,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        )
                        .padding(horizontal = 12.dp, vertical = 12.0.dp)
                        .fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier.padding(end = 8.dp),
                            painter = painterResource(id = R.drawable.ic_refresh),
                            contentDescription = "Refresh",
                        )

                        Text(
                            text = stringResource(R.string.file_converter),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color.Black,
                            ),
                        )
                    }
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.recent_file),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
            ),
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}