package modules.innertube.models.body

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Context

data class SearchBody(
    @SerializedName("context") val context: Context,
    @SerializedName("query") val query: String?,
    @SerializedName("params") val params: String?,
)
