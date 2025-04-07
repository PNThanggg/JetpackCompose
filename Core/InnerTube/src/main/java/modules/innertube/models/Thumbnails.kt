package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class Thumbnails(
    @SerializedName("thumbnails") val thumbnails: List<Thumbnail>,
)

data class Thumbnail(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height: Int?,
)
