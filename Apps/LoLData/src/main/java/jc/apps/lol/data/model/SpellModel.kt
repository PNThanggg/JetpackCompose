package jc.apps.lol.data.model

import com.google.gson.annotations.SerializedName

data class SpellModel(
    @SerializedName("description") val description: String? = "",
    @SerializedName("id") val id: String? = "",
    @SerializedName("image") val image: ImageModel? = ImageModel(),
    @SerializedName("name") val name: String? = ""
)