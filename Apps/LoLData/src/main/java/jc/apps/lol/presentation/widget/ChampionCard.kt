package jc.apps.lol.presentation.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jc.apps.lol.data.datasource.remote.ApiConstants
import jc.apps.lol.data.model.ChampionModel

@Composable
fun ChampionCard(
    modifier: Modifier = Modifier,
    champion: ChampionModel,
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        AsyncImage(
            model = ApiConstants.IMAGE_LOADING_URL + "${champion.name}_0.jpg",
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .weight(0.3f)
                .height(180.dp)
        )

        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = champion.name ?: "",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = champion.blurb ?: "",
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 24.sp
            )
        }
    }
}