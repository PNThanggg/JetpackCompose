package jc.apps.music.models

import jc.apps.music.db.entities.LocalItem
import modules.innertube.models.YTItem

data class SimilarRecommendation(
    val title: LocalItem,
    val items: List<YTItem>,
)
