package jc.apps.lol.data.model

import com.google.gson.annotations.SerializedName

data class ChampionModel(
    @SerializedName("id") val id: String? = "",
    @SerializedName("image") val image: ImageModel? = ImageModel(),
    @SerializedName("key") val key: String? = "",
    @SerializedName("lore") val lore: String? = "",
    @SerializedName("blurb") val blurb: String? = "",
    @SerializedName("name") val name: String? = "",
    @SerializedName("passive") val passive: PassiveModel? = PassiveModel(),
    @SerializedName("spells") val spells: List<SpellModel> = listOf(),
    @SerializedName("tags") val tags: List<String> = listOf(),
    @SerializedName("title") val title: String? = ""
)