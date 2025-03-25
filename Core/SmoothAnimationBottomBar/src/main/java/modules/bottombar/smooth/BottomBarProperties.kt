package modules.bottombar.smooth

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BottomBarProperties(
    var backgroundColor: Color = Blue,
    var height: Dp = 56.dp,
    var indicatorColor: Color = Color.White.copy(alpha = 0.2F),
    var iconTintColor: Color = BlueTint,
    var iconTintActiveColor: Color = Color.White,
    var textActiveColor: Color = Color.White,
    var cornerRadius: Dp = 8.dp,
    var fontFamily: FontFamily = FontFamily.Default,
    var fontSize: TextUnit = 16.sp,
    var fontWeight: FontWeight = FontWeight.Normal
)