package jc.app.bank_pick.activity.main.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Point(val x: Float, val y: Float)

val graphDataPoints_Long = listOf(
    0.3f, 0.5f, 0.2f, 0.7f, 0.45f, 0.85f, 0.6f, 0.4f, 0.7f, 0.9f, 0.5f, 0.3f
)
val monthLabels_Long = listOf(
    "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)

val graphDataPoints = listOf(0.3f, 0.5f, 0.2f, 0.7f, 0.45f, 0.85f)
val monthLabels = listOf("Oct", "Nov", "Dec", "Jan", "Feb", "Mar")
val primaryBlue = Color(0xFF0D82FF)
val lightBlue = Color(0xFF8ECBFF)
val textGray = Color.Gray

@Composable
fun GraphCanvas(
    modifier: Modifier = Modifier,
    dataPoints: List<Float>,
    selectedIndex: Int,
    lineColor: Color = primaryBlue,
    gradientStartColor: Color = primaryBlue.copy(alpha = 0.4f),
    gradientEndColor: Color = Color.Transparent,
    pointHighlightColor: Color = primaryBlue,
    pointHighlightBorderColor: Color = Color.White,
    axisLineColor: Color = Color.LightGray.copy(alpha = 0.6f),
    smoothness: Float = 0.2f
) {
    // Các tính toán bên trong Canvas giờ sẽ hoạt động với size.width
    // là chiều rộng đầy đủ đã được tính toán bởi parent.
    val density = LocalDensity.current
    val strokeWidthPx = with(density) { 3.dp.toPx() }
    val pointRadiusPx = with(density) { 6.dp.toPx() }
    val pointBorderPx = with(density) { 2.dp.toPx() }
    val axisStrokeWidthPx = with(density) { 1.dp.toPx() }

    Canvas(modifier = modifier) {
        if (dataPoints.size < 2) return@Canvas

        val points = mutableListOf<Point>()
        val xSpacing = size.width / (dataPoints.size - 1).coerceAtLeast(1)
        val yMax = dataPoints.maxOrNull() ?: 1.0f
        val yMin = dataPoints.minOrNull() ?: 0.0f
        val yRange = (yMax - yMin).coerceAtLeast(0.1f)
        val yPaddingFactor = 0.1f
        val scaleY = size.height * (1 - 2 * yPaddingFactor) / yRange
        val offsetY = size.height * yPaddingFactor + (yMax * scaleY)

        dataPoints.forEachIndexed { index, value ->
            val x = index * xSpacing
            val y = offsetY - (value * scaleY)
            points.add(Point(x, y))
        }

        val linePath = Path()
        val fillPath = Path()
        fun getPoint(index: Int): Point {
            return points.getOrElse(index) {
                points.getOrNull(index.coerceIn(points.indices)) ?: Point(0f, 0f)
            }
        }
        linePath.moveTo(points[0].x, points[0].y)
        fillPath.moveTo(points[0].x, size.height); fillPath.lineTo(points[0].x, points[0].y)
        for (i in 0 until points.size - 1) {
            val p0 = getPoint(i - 1);
            val p1 = getPoint(i);
            val p2 = getPoint(i + 1);
            val p3 = getPoint(i + 2)
            val cp1x = p1.x + (p2.x - p0.x) * smoothness;
            val cp1y = p1.y + (p2.y - p0.y) * smoothness
            val cp2x = p2.x - (p3.x - p1.x) * smoothness;
            val cp2y = p2.y - (p3.y - p1.y) * smoothness
            linePath.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
            fillPath.cubicTo(cp1x, cp1y, cp2x, cp2y, p2.x, p2.y)
        }
        fillPath.lineTo(points.last().x, size.height); fillPath.close()

        // --- Draw elements ---
        points.forEach { point ->
            drawLine(
                color = axisLineColor,
                start = Offset(point.x, size.height),
                end = Offset(point.x, 0f),
                strokeWidth = axisStrokeWidthPx
            )
        }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(gradientStartColor, gradientEndColor),
                startY = points.minOfOrNull { it.y } ?: 0f,
                endY = size.height),
            style = Fill)
        drawPath(
            path = linePath,
            color = lineColor,
            style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        if (selectedIndex in points.indices) {
            val selectedPointOffset = Offset(points[selectedIndex].x, points[selectedIndex].y)
            drawCircle(
                color = pointHighlightBorderColor,
                radius = pointRadiusPx + pointBorderPx,
                center = selectedPointOffset
            )
            drawCircle(
                color = pointHighlightColor, radius = pointRadiusPx, center = selectedPointOffset
            )
        }
    }
}

@Composable
fun MonthSelector(
    modifier: Modifier = Modifier,
    months: List<String>,
    selectedMonth: String,
    onMonthSelected: (String) -> Unit,
    minWidthPerItem: Dp = 60.dp
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        months.forEach { month ->
            val isSelected = month == selectedMonth
            val backgroundColor = if (isSelected) primaryBlue else Color.Transparent
            val textColor = if (isSelected) Color.White else textGray
            val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

            Box(
                modifier = Modifier
                .widthIn(min = minWidthPerItem)
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor)
                    .clickable { onMonthSelected(month) }
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center) {
                Text(
                    text = month,
                    color = textColor,
                    fontSize = 14.sp,
                    fontWeight = fontWeight,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

fun max(a: Dp, b: Dp): Dp {
    return if (a >= b) a else b
}

@Composable
fun MonthlyGraphView(
    modifier: Modifier = Modifier,
    data: List<Float> = graphDataPoints_Long,
    labels: List<String> = monthLabels_Long,
    initialSelectedMonth: String = labels.firstOrNull() ?: ""
) {
    var selectedMonth by remember { mutableStateOf(initialSelectedMonth) }
    val selectedIndex = labels.indexOf(selectedMonth).coerceAtLeast(0)

    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val minWidthPerItem = 60.dp

    val totalCalculatedWidth = with(density) {
        (labels.size * minWidthPerItem.toPx()).toDp()
    }
    val totalWidth = max(totalCalculatedWidth, screenWidth)


    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier.width(totalWidth)
            ) {
                GraphCanvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    dataPoints = data,
                    selectedIndex = selectedIndex,
                )

                Spacer(modifier = Modifier.height(8.dp))

                MonthSelector(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    months = labels,
                    selectedMonth = selectedMonth,
                    onMonthSelected = { selectedMonth = it },
                    minWidthPerItem = minWidthPerItem
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// --- Previews ---
@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5, widthDp = 360)
@Composable
fun DefaultPreview_ShortData() {
    MaterialTheme {
        MonthlyGraphView(
            data = graphDataPoints,
            labels = monthLabels
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5, widthDp = 360)
@Composable
fun DefaultPreview_LongData() {
    MaterialTheme {
        MonthlyGraphView(
            data = graphDataPoints_Long,
            labels = monthLabels_Long,
            initialSelectedMonth = monthLabels_Long.first()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F5F5, widthDp = 360)
@Composable
fun NovSelectedPreview_LongData() {
    MaterialTheme {
        MonthlyGraphView(
            data = graphDataPoints_Long,
            labels = monthLabels_Long,
            initialSelectedMonth = "Nov"
        )
    }
}