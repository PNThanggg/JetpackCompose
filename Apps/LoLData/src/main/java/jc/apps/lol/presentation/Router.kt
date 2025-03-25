package jc.apps.lol.presentation

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object Splash

@Serializable
data object ChampionList

@Serializable
data class ChampionDetails(val name: String)