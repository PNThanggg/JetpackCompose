package modules.innertube.models.body

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Context

data class GetSearchSuggestionsBody(
    @SerializedName("context") val context: Context,
    @SerializedName("input") val input: String,
)
