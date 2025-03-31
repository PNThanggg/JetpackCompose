package jc.app.bank_pick.screen.home.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import jc.app.bank_pick.R

@Preview(
    showBackground = true,
)
@Composable
fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 4.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            contentDescription = stringResource(R.string.app_name),
            painter = painterResource(R.drawable.img_avatar),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = "${stringResource(R.string.welcome_back)}, ",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF7E848D),
                    fontSize = TextUnit(12F, TextUnitType.Sp),
                    fontWeight = FontWeight.W400,
                ),
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Tanya Myroniuk",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF1E1E2D),
                    fontSize = TextUnit(18F, TextUnitType.Sp),
                    fontWeight = FontWeight.W600,
                ),
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(42.dp)
                .background(
                    shape = CircleShape,
                    color = Color(0xFFF4F4F4),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                contentDescription = stringResource(R.string.app_name),
                painter = painterResource(R.drawable.ic_search),
            )
        }
    }
}