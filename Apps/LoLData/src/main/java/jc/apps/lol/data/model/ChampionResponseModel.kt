package jc.apps.lol.data.model

import com.google.gson.annotations.SerializedName

data class ChampionResponseModel(
    @SerializedName("data") val champion: Map<String, ChampionModel> = emptyMap(),
    @SerializedName("format") val format: String? = "",
    @SerializedName("type") val type: String? = "",
    @SerializedName("version") val version: String? = ""
)

fun Map<String, ChampionModel>.toChampionList(): List<ChampionModel> = this.values.toList()