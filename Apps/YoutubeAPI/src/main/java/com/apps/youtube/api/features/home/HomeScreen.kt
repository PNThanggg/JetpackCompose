package com.apps.youtube.api.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.apps.youtube.api.R
import com.apps.youtube.api.data.models.Categories
import com.apps.youtube.api.features.home.widget.HomeHeader

@PreviewLightDark()
@Composable
fun HomeScreen() {
    val isDarkTheme = isSystemInDarkTheme()
    val categories = Categories.categoryMap.keys.toList()

    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .background(
                    if (isDarkTheme) Color(0xFF0F0F0F) else Color(0xFFF5F5F5)
                )
                .fillMaxSize(),
        ) {
            HomeHeader()

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(categories.size) { index ->
                    if (index == 0) Box(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                            )
                            .background(
                                color = if (!isDarkTheme) Color(0xFF0F0F0F) else Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(8.dp),
                            ),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_discover),
                            contentDescription = "Discover",
                            tint = if (!isDarkTheme) Color.White else Color.Black,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        )
                    }

                    Box(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = if (index == categories.size - 1) 12.dp else 0.dp
                            )
                            .background(
                                color = if (!isDarkTheme) Color(0xFF0F0F0F) else Color(0xFFF5F5F5),
                                shape = RoundedCornerShape(8.dp),
                            ),
                    ) {
                        Text(
                            text = categories[index],
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = if (!isDarkTheme) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}