package modules.innertube.models.body

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Context

data class GetQueueBody(
    @SerializedName("context") val context: Context,
    @SerializedName("videoIds") val videoIds: List<String>?,
    @SerializedName("playlistId") val playlistId: String?,
)
