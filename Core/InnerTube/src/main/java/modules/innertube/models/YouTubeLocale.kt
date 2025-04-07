package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class YouTubeLocale(
    @SerializedName("gl") val gl: String, // geolocation
    @SerializedName("hl") val hl: String, // host language
)
