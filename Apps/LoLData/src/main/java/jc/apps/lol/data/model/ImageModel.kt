package jc.apps.lol.data.model

import com.google.gson.annotations.SerializedName

data class ImageModel(
    @SerializedName("full") val full: String? = "",
    @SerializedName("group") val group: String? = ""
)