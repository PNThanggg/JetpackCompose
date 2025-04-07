package modules.innertube.models.body

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Context

data class GetTranscriptBody(
    @SerializedName("context") val context: Context,
    @SerializedName("params") val params: String,
)
