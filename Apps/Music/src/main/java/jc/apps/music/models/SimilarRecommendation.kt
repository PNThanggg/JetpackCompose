package jc.apps.music.models

data class SimilarRecommendation(
    val title: LocalItem,
    val items: List<YTItem>,
)
