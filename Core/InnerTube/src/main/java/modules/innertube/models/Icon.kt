package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class Icon(
    @SerializedName("iconType") val iconType: String,
)