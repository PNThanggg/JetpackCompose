package modules.innertube.models.response

import com.google.gson.annotations.SerializedName

/**
 * PlayerResponse with [YouTubeClient.ANDROID_MUSIC] client
 */
data class PlayerResponse(
    @SerializedName("responseContext") val responseContext: ResponseContext,
    @SerializedName("playabilityStatus") val playabilityStatus: PlayabilityStatus,
    @SerializedName("playerConfig") val playerConfig: PlayerConfig?,
    @SerializedName("streamingData") val streamingData: StreamingData?,
    @SerializedName("videoDetails") val videoDetails: VideoDetails?,
) {
    data class PlayabilityStatus(
        @SerializedName("status") val status: String,
        @SerializedName("reason") val reason: String?,
    )

    data class PlayerConfig(
        @SerializedName("audioConfig") val audioConfig: AudioConfig,
    ) {
        data class AudioConfig(
            @SerializedName("loudnessDb") val loudnessDb: Double?,
            @SerializedName("perceptualLoudnessDb") val perceptualLoudnessDb: Double?,
        )
    }

    data class StreamingData(
        @SerializedName("formats") val formats: List<Format>?,
        @SerializedName("adaptiveFormats") val adaptiveFormats: List<Format>,
        @SerializedName("expiresInSeconds") val expiresInSeconds: Int,
    ) {
        data class Format(
            @SerializedName("itag") val itag: Int,
            @SerializedName("url") val url: String?,
            @SerializedName("mimeType") val mimeType: String,
            @SerializedName("bitrate") val bitrate: Int,
            @SerializedName("width") val width: Int?,
            @SerializedName("height") val height: Int?,
            @SerializedName("contentLength") val contentLength: Long?,
            @SerializedName("quality") val quality: String,
            @SerializedName("fps") val fps: Int?,
            @SerializedName("qualityLabel") val qualityLabel: String?,
            @SerializedName("averageBitrate") val averageBitrate: Int?,
            @SerializedName("audioQuality") val audioQuality: String?,
            @SerializedName("approxDurationMs") val approxDurationMs: String?,
            @SerializedName("audioSampleRate") val audioSampleRate: Int?,
            @SerializedName("audioChannels") val audioChannels: Int?,
            @SerializedName("loudnessDb") val loudnessDb: Double?,
            @SerializedName("lastModified") val lastModified: Long?,
        ) {
            val isAudio: Boolean
                get() = width == null
        }
    }

    data class VideoDetails(
        @SerializedName("videoId") val videoId: String,
        @SerializedName("title") val title: String,
        @SerializedName("author") val author: String,
        @SerializedName("channelId") val channelId: String,
        @SerializedName("lengthSeconds") val lengthSeconds: String,
        @SerializedName("musicVideoType") val musicVideoType: String?,
        @SerializedName("viewCount") val viewCount: String,
        @SerializedName("thumbnail") val thumbnail: Thumbnails,
    )
}