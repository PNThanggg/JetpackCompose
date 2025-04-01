package jc.app.bank_pick.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import jc.app.bank_pick.R
import jc.app.bank_pick.screen.home.widget.HomeHeader
import jc.app.bank_pick.screen.home.widget.ItemRow
import jc.app.bank_pick.widget.CardWidget

@Preview(showBackground = true)
@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 12.dp, end = 12.dp)
    ) {
        HomeHeader()

        Spacer(modifier = Modifier.height(12.dp))

        CardWidget()

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            ItemRow(
                title = stringResource(R.string.sent),
                icon = R.drawable.ic_send,
            )

            ItemRow(
                title = stringResource(R.string.receive),
                icon = R.drawable.ic_receive,
            )

            ItemRow(
                title = stringResource(R.string.currency),
                icon = R.drawable.ic_currency,
            )

            ItemRow(
                title = stringResource(R.string.topup),
                icon = R.drawable.ic_topup,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "Transaction",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF1E1E2D),
                    fontSize = TextUnit(18F, TextUnitType.Unspecified),
                    fontWeight = FontWeight.W600,
                    lineHeight = TextUnit(18F, TextUnitType.Unspecified),
                ),
            )

            Text(
                "See All",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = Color(0xFF0066FF),
                    fontSize = TextUnit(14F, TextUnitType.Unspecified),
                    fontWeight = FontWeight.W500,
                    lineHeight = TextUnit(14F, TextUnitType.Unspecified),
                ),
            )
        }
    }
}
