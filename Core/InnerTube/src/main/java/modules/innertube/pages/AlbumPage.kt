package modules.innertube.pages

import modules.innertube.models.Album
import modules.innertube.models.AlbumItem
import modules.innertube.models.Artist
import modules.innertube.models.MusicResponsiveListItemRenderer
import modules.innertube.models.SongItem
import modules.innertube.models.oddElements
import modules.innertube.utils.parseTime

data class AlbumPage(
    val album: AlbumItem,
    val songs: List<SongItem>,
    val otherVersions: List<AlbumItem>,
) {
    companion object {
        fun fromMusicResponsiveListItemRenderer(
            renderer: MusicResponsiveListItemRenderer
        ): SongItem? {
            return SongItem(
                id = renderer.playlistItemData?.videoId ?: return null,
                title = renderer.flexColumns.firstOrNull()?.musicResponsiveListItemFlexColumnRenderer?.text?.runs?.firstOrNull()?.text
                    ?: return null,
                artists = renderer.flexColumns.getOrNull(1)?.musicResponsiveListItemFlexColumnRenderer?.text?.runs?.oddElements()
                    ?.map {
                        Artist(
                            name = it.text, id = it.navigationEndpoint?.browseEndpoint?.browseId
                        )
                    } ?: return null,
                album = renderer.flexColumns.getOrNull(2)?.musicResponsiveListItemFlexColumnRenderer?.text?.runs?.firstOrNull()
                    ?.let {
                        Album(
                            name = it.text, id = it.navigationEndpoint?.browseEndpoint?.browseId!!
                        )
                    } ?: return null,
                duration = renderer.fixedColumns?.firstOrNull()?.musicResponsiveListItemFlexColumnRenderer?.text?.runs?.firstOrNull()?.text?.parseTime()
                    ?: return null,
                thumbnail = renderer.thumbnail?.musicThumbnailRenderer?.getThumbnailUrl()
                    ?: return null,
                explicit = renderer.badges?.find {
                    it.musicInlineBadgeRenderer?.icon?.iconType == "MUSIC_EXPLICIT_BADGE"
                } != null)
        }
    }
}