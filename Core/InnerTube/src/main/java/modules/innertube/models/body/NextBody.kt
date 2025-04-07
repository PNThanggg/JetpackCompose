package modules.innertube.models.body

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Context

data class NextBody(
    @SerializedName("context") val context: Context,
    @SerializedName("videoId") val videoId: String?,
    @SerializedName("playlistId") val playlistId: String?,
    @SerializedName("playlistSetVideoId") val playlistSetVideoId: String?,
    @SerializedName("index") val index: Int?,
    @SerializedName("params") val params: String?,
    @SerializedName("continuation") val continuation: String?,
)
