package modules.innertube.models

import com.google.gson.annotations.SerializedName
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_ALBUM
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_ARTIST
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_AUDIOBOOK
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_PLAYLIST

sealed class Endpoint


data class WatchEndpoint(
    @SerializedName("videoId") val videoId: String? = null,
    @SerializedName("playlistId") val playlistId: String? = null,
    @SerializedName("playlistSetVideoId") val playlistSetVideoId: String? = null,
    @SerializedName("params") val params: String? = null,
    @SerializedName("index") val index: Int? = null,
    @SerializedName("watchEndpointMusicSupportedConfigs") val watchEndpointMusicSupportedConfigs: WatchEndpointMusicSupportedConfigs? = null,
) : Endpoint() {
    data class WatchEndpointMusicSupportedConfigs(
        @SerializedName("watchEndpointMusicConfig") val watchEndpointMusicConfig: WatchEndpointMusicConfig,
    ) {
        data class WatchEndpointMusicConfig(
            @SerializedName("musicVideoType") val musicVideoType: String,
        ) {
            companion object {
                const val MUSIC_VIDEO_TYPE_OMV = "MUSIC_VIDEO_TYPE_OMV"
                const val MUSIC_VIDEO_TYPE_UGC = "MUSIC_VIDEO_TYPE_UGC"
                const val MUSIC_VIDEO_TYPE_ATV = "MUSIC_VIDEO_TYPE_ATV"
            }
        }
    }
}

data class BrowseEndpoint(
    @SerializedName("browseId") val browseId: String,
    @SerializedName("params") val params: String? = null,
    @SerializedName("browseEndpointContextSupportedConfigs") val browseEndpointContextSupportedConfigs: BrowseEndpointContextSupportedConfigs? = null,
) : Endpoint() {
    val isArtistEndpoint: Boolean
        get() = browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_ARTIST
    val isAlbumEndpoint: Boolean
        get() = browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_ALBUM || browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_AUDIOBOOK
    val isPlaylistEndpoint: Boolean
        get() = browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_PLAYLIST

    data class BrowseEndpointContextSupportedConfigs(
        @SerializedName("browseEndpointContextMusicConfig") val browseEndpointContextMusicConfig: BrowseEndpointContextMusicConfig,
    ) {
        data class BrowseEndpointContextMusicConfig(
            @SerializedName("pageType") val pageType: String,
        ) {
            companion object {
                const val MUSIC_PAGE_TYPE_ALBUM = "MUSIC_PAGE_TYPE_ALBUM"
                const val MUSIC_PAGE_TYPE_AUDIOBOOK = "MUSIC_PAGE_TYPE_AUDIOBOOK"
                const val MUSIC_PAGE_TYPE_PLAYLIST = "MUSIC_PAGE_TYPE_PLAYLIST"
                const val MUSIC_PAGE_TYPE_ARTIST = "MUSIC_PAGE_TYPE_ARTIST"
                const val MUSIC_PAGE_TYPE_USER_CHANNEL = "MUSIC_PAGE_TYPE_USER_CHANNEL"
                const val MUSIC_PAGE_TYPE_TRACK_LYRICS = "MUSIC_PAGE_TYPE_TRACK_LYRICS"
                const val MUSIC_PAGE_TYPE_TRACK_RELATED = "MUSIC_PAGE_TYPE_TRACK_RELATED"
            }
        }
    }
}

data class SearchEndpoint(
    @SerializedName("params") val params: String?,
    @SerializedName("query") val query: String,
) : Endpoint()

data class QueueAddEndpoint(
    @SerializedName("queueInsertPosition") val queueInsertPosition: String,
    @SerializedName("queueTarget") val queueTarget: QueueTarget,
) : Endpoint() {
    data class QueueTarget(
        @SerializedName("videoId") val videoId: String? = null,
        @SerializedName("playlistId") val playlistId: String? = null,
    )
}

data class ShareEntityEndpoint(
    @SerializedName("serializedShareEntity") val serializedShareEntity: String,
) : Endpoint()
