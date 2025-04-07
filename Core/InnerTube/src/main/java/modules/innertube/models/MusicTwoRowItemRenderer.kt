package modules.innertube.models

import com.google.gson.annotations.SerializedName
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_ALBUM
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_ARTIST
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_AUDIOBOOK
import modules.innertube.models.BrowseEndpoint.BrowseEndpointContextSupportedConfigs.BrowseEndpointContextMusicConfig.Companion.MUSIC_PAGE_TYPE_PLAYLIST

/**
 * Two row: a big thumbnail, a title, and a subtitle
 * Used in [GridRenderer] and [MusicCarouselShelfRenderer]
 * Item type: song, video, album, playlist, artist
 */
data class MusicTwoRowItemRenderer(
    @SerializedName("title") val title: Runs,
    @SerializedName("subtitle") val subtitle: Runs? = null,
    @SerializedName("subtitleBadges") val subtitleBadges: List<Badges>? = null,
    @SerializedName("menu") val menu: Menu? = null,
    @SerializedName("thumbnailRenderer") val thumbnailRenderer: ThumbnailRenderer,
    @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint,
    @SerializedName("thumbnailOverlay") val thumbnailOverlay: MusicResponsiveListItemRenderer.Overlay? = null
) {
    val isSong: Boolean
        get() = navigationEndpoint.endpoint is WatchEndpoint
    val isPlaylist: Boolean
        get() = navigationEndpoint.browseEndpoint?.browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_PLAYLIST
    val isAlbum: Boolean
        get() = navigationEndpoint.browseEndpoint?.browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_ALBUM || navigationEndpoint.browseEndpoint?.browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_AUDIOBOOK
    val isArtist: Boolean
        get() = navigationEndpoint.browseEndpoint?.browseEndpointContextSupportedConfigs?.browseEndpointContextMusicConfig?.pageType == MUSIC_PAGE_TYPE_ARTIST
}