package modules.core.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

val AppFont = FontFamily(
    Font(R.font.space_grotesk_regular, weight = FontWeight.Normal),
    Font(R.font.space_grotesk_light, weight = FontWeight.Light),
    Font(R.font.space_grotesk_medium, weight = FontWeight.Medium),
    Font(R.font.space_grotesk_semibold, weight = FontWeight.Bold),
)