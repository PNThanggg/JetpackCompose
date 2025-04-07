package modules.innertube.models

import com.google.gson.annotations.SerializedName
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_ALBUM
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_ARTIST
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_AUDIOBOOK
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_PLAYLIST

/**
 * Typical list item
 * Used in [MusicCarouselShelfRenderer], [MusicShelfRenderer]
 * Appears in quick picks, search results, table items, etc.
 */
data class MusicResponsiveListItemRenderer(
    @SerializedName("badges") val badges: List<Badges>? = null,
    @SerializedName("fixedColumns") val fixedColumns: List<FlexColumn>? = null,
    @SerializedName("flexColumns") val flexColumns: List<FlexColumn>,
    @SerializedName("thumbnail") val thumbnail: ThumbnailRenderer? = null,
    @SerializedName("menu") val menu: Menu? = null,
    @SerializedName("playlistItemData") val playlistItemData: PlaylistItemData? = null,
    @SerializedName("overlay") val overlay: Overlay? = null,
    @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint? = null
) {
    val isSong: Boolean
        get() = navigationEndpoint == null || navigationEndpoint.watchEndpoint != null || navigationEndpoint.watchPlaylistEndpoint != null
    val isPlaylist: Boolean
        get() = navigationEndpoint?.browseEndpoint?.browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_PLAYLIST
    val isAlbum: Boolean
        get() = navigationEndpoint?.browseEndpoint?.browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_ALBUM || navigationEndpoint?.browseEndpoint?.browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_AUDIOBOOK
    val isArtist: Boolean
        get() = navigationEndpoint?.browseEndpoint?.browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_ARTIST

    data class FlexColumn(
        @SerializedName("musicResponsiveListItemFlexColumnRenderer") val musicResponsiveListItemFlexColumnRenderer: MusicResponsiveListItemFlexColumnRenderer
    ) {
        data class MusicResponsiveListItemFlexColumnRenderer(
            @SerializedName("text") val text: Runs? = null
        )
    }

    data class PlaylistItemData(
        @SerializedName("playlistSetVideoId") val playlistSetVideoId: String? = null,
        @SerializedName("videoId") val videoId: String
    )

    data class Overlay(
        @SerializedName("musicItemThumbnailOverlayRenderer") val musicItemThumbnailOverlayRenderer: MusicItemThumbnailOverlayRenderer
    ) {
        data class MusicItemThumbnailOverlayRenderer(
            @SerializedName("content") val content: Content
        ) {
            data class Content(
                @SerializedName("musicPlayButtonRenderer") val musicPlayButtonRenderer: MusicPlayButtonRenderer
            ) {
                data class MusicPlayButtonRenderer(
                    @SerializedName("playNavigationEndpoint") val playNavigationEndpoint: NavigationEndpoint? = null
                )
            }
        }
    }
}