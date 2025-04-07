package modules.innertube.models.body

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Context

data class BrowseBody(
    @SerializedName("context") val context: Context,
    @SerializedName("browseId") val browseId: String?,
    @SerializedName("params") val params: String?,
)
