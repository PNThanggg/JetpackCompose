package com.apps.youtube.api.features.home.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.apps.youtube.api.R
import com.apps.youtube.api.data.models.Categories

@PreviewLightDark()
@Composable
fun CategoriesLine(
    openDrawer: () -> Unit = {},
) {
    val categories = Categories.categoryMap.keys.toList()
    val isDarkTheme = isSystemInDarkTheme()

    var selectedIndex by remember { mutableIntStateOf(0) }

    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(categories.size) { index ->
            if (index == 0) Row(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                    )
                    .background(
                        color = if (isDarkTheme) Color(0xFF3F3F3F) else Color(0xFFE5E5E5),
                        shape = RoundedCornerShape(8.dp),
                    )
                    .clickable {
                        openDrawer.invoke()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_discover),
                    contentDescription = "Discover",
                    tint = if (isDarkTheme) Color.White else Color.Black,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Explore", color = if (isDarkTheme) {
                        Color.White
                    } else {
                        Color.Black
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))
            }

            Box(
                modifier = Modifier
                    .padding(
                        start = 12.dp, end = if (index == categories.size - 1) 12.dp else 0.dp
                    )
                    .background(
                        color = if (isDarkTheme) {
                            if (selectedIndex == index) Color.White else Color(0xFF3F3F3F)
                        } else {
                            if (selectedIndex == index) Color(0xFF030303) else Color(0xFFE5E5E5)
                        },
                        shape = RoundedCornerShape(8.dp),
                    )
                    .clickable {
                        selectedIndex = index
                    },
            ) {
                Text(
                    text = categories[index],
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = if (isDarkTheme) {
                        if (selectedIndex == index) Color.Black else Color.White
                    } else {
                        if (selectedIndex == index) Color.White else Color.Black
                    }
                )
            }
        }
    }
}