package jc.apps.lol.presentation.home

import jc.apps.lol.data.model.ChampionModel

data class HomeUIState(
    val searchText: String = "",
    val champions: List<ChampionModel> = emptyList(),
    val filteredChampions: List<ChampionModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)