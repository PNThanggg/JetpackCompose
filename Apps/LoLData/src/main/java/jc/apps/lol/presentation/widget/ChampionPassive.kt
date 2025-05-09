package jc.apps.lol.presentation.widget

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jc.apps.lol.data.datasource.remote.ApiConstants
import jc.apps.lol.data.model.PassiveModel

@Composable
fun ChampionPassive(passive: PassiveModel, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = {
            Text(text = passive.name ?: "")
        }, supportingContent = {
            Text(text = passive.description ?: "")
        }, leadingContent = {
            AsyncImage(
                model = ApiConstants.IMAGE_PASSIVE_URL + "${passive.image?.full}",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }, modifier = modifier
    )
}