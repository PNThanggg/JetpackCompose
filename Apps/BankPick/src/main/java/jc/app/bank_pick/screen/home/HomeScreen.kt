package jc.app.bank_pick.screen.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jc.app.bank_pick.R
import jc.app.bank_pick.screen.home.widget.HomeHeader
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

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Transaction")

            Text("Sell All")
        }
    }
}

@Composable
fun ItemRow(
    title: String, @DrawableRes icon: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .background(
                    shape = CircleShape,
                    color = Color(0xFFF4F4F4),
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                contentDescription = stringResource(R.string.app_name),
                painter = painterResource(icon),
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title
        )
    }
}