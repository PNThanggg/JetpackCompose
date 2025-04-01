package jc.app.bank_pick.activity.main.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import jc.app.bank_pick.R

@Composable
fun CardWidget() {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp)),
            painter = painterResource(R.drawable.bg_card),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(
                    modifier = Modifier.size(29.dp, 25.dp),
                    painter = painterResource(R.drawable.ic_chips),
                    contentDescription = stringResource(R.string.app_name)
                )

                Spacer(modifier = Modifier.weight(1F))

                Image(
                    modifier = Modifier.size(16.dp, 22.dp),
                    painter = painterResource(R.drawable.ic_union),
                    contentDescription = stringResource(R.string.app_name)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "4562   1122   4595   7852",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = TextUnit(24F, TextUnitType.Sp),
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "AR Jonson", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.W400,
                    fontSize = TextUnit(12F, TextUnitType.Sp),
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.weight(1F))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Expiry Date", style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.W400,
                            fontSize = TextUnit(9F, TextUnitType.Sp),
                            color = Color(0xFFA2A2A7)
                        )
                    )

                    Text(
                        text = "24/2000", style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.W400,
                            fontSize = TextUnit(12F, TextUnitType.Sp),
                            color = Color.White
                        )
                    )

                }

                Spacer(modifier = Modifier.width(24.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "CVV", style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.W400,
                            fontSize = TextUnit(9F, TextUnitType.Sp),
                            color = Color(0xFFA2A2A7)
                        )
                    )

                    Text(
                        text = "6986", style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.W400,
                            fontSize = TextUnit(12F, TextUnitType.Sp),
                            color = Color.White
                        )
                    )

                }

                Spacer(modifier = Modifier.weight(1F))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(36.dp, 20.dp),
                        painter = painterResource(R.drawable.ic_mastercard),
                        contentDescription = stringResource(R.string.app_name)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Mastercard", style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.W400,
                            fontSize = TextUnit(12F, TextUnitType.Sp),
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}