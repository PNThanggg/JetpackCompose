package modules.innertube.models.response

import com.google.gson.annotations.SerializedName

data class PipedResponse(
    @SerializedName("audioStreams") val audioStreams: List<AudioStream>
) {
    data class AudioStream(
        @SerializedName("itag") val itag: Int,
        @SerializedName("url") val url: String,
        @SerializedName("bitrate") val bitrate: Int
    )
}