package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class MusicDescriptionShelfRenderer(
    @SerializedName("header") val header: Runs? = null,
    @SerializedName("subheader") val subheader: Runs? = null,
    @SerializedName("description") val description: Runs,
    @SerializedName("footer") val footer: Runs? = null
)