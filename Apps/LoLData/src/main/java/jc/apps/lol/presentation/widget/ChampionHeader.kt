package jc.apps.lol.presentation.widget


import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import jc.apps.lol.data.datasource.remote.ApiConstants
import jc.apps.lol.data.model.ChampionModel


@Composable
fun ChampionHeader(
    champion: ChampionModel, modifier: Modifier = Modifier
) {
    ListItem(modifier = modifier, headlineContent = {
        Text(
            text = champion.name ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold
        )
    }, supportingContent = {
        Text(
            text = champion.tags.firstOrNull() ?: ""
        )
    }, leadingContent = {
        AsyncImage(
            model = ApiConstants.IMAGE_SQUARE_URL + "${champion.name}.png",
            contentDescription = null,
            modifier = Modifier.size(40.dp)
        )
    }, trailingContent = {
        Text(
            text = champion.title ?: "",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    })
}

