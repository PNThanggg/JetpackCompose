package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class ThumbnailRenderer(
    @SerializedName("musicThumbnailRenderer") val musicThumbnailRenderer: MusicThumbnailRenderer?,
    @SerializedName("musicAnimatedThumbnailRenderer") val musicAnimatedThumbnailRenderer: MusicAnimatedThumbnailRenderer?,
    @SerializedName("croppedSquareThumbnailRenderer") val croppedSquareThumbnailRenderer: MusicThumbnailRenderer?,
) {
    data class MusicThumbnailRenderer(
        @SerializedName("thumbnail") val thumbnail: Thumbnails,
        @SerializedName("thumbnailCrop") val thumbnailCrop: String?,
        @SerializedName("thumbnailScale") val thumbnailScale: String?,
    ) {
        fun getThumbnailUrl() = thumbnail.thumbnails.lastOrNull()?.url
    }

    data class MusicAnimatedThumbnailRenderer(
        @SerializedName("animatedThumbnail") val animatedThumbnail: Thumbnails,
        @SerializedName("backupRenderer") val backupRenderer: MusicThumbnailRenderer,
    )
}
