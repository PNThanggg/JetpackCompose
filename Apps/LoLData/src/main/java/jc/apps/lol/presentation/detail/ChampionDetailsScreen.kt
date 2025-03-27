package jc.apps.lol.presentation.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import jc.apps.lol.data.datasource.remote.ApiConstants
import jc.apps.lol.data.model.ChampionModel
import jc.apps.lol.presentation.widget.ChampionHeader
import jc.apps.lol.presentation.widget.ChampionLore
import jc.apps.lol.presentation.widget.ChampionPassive
import jc.apps.lol.presentation.widget.ChampionSpell


@Composable
fun ChampionDetailsScreen(
    champion: ChampionModel
) {
    Scaffold { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            item {
                AsyncImage(
                    model = ApiConstants.IMAGE_SPLASH_URL + "${champion.name}_0.jpg",
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                ChampionHeader(
                    champion = champion, modifier = Modifier.padding(
                        horizontal = 10.dp, vertical = 6.dp
                    )
                )

                ChampionLore(
                    lore = champion.lore ?: "", modifier = Modifier.padding(
                        horizontal = 20.dp, vertical = 6.dp
                    )
                )

                champion.passive?.let { passive ->
                    ChampionPassive(
                        passive = passive, modifier = Modifier.padding(
                            horizontal = 6.dp, vertical = 10.dp
                        )
                    )
                }

                champion.spells.forEach { spell ->
                    ChampionSpell(
                        spell = spell, modifier = Modifier.padding(
                            horizontal = 6.dp, vertical = 10.dp
                        )
                    )
                }
            }
        }
    }
}