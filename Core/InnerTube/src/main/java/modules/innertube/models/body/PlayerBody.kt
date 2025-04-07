package modules.innertube.models.body

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Context

data class PlayerBody(
    @SerializedName("context") val context: Context,
    @SerializedName("videoId") val videoId: String,
    @SerializedName("playlistId") val playlistId: String?,
    @SerializedName("contentCheckOk") val contentCheckOk: Boolean = true,
)
