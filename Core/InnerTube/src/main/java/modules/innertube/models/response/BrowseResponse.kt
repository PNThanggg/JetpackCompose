package modules.innertube.models.response

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Button
import modules.innertube.models.Continuation
import modules.innertube.models.GridRenderer
import modules.innertube.models.Menu
import modules.innertube.models.MusicShelfRenderer
import modules.innertube.models.ResponseContext
import modules.innertube.models.Runs
import modules.innertube.models.SectionListRenderer
import modules.innertube.models.Tabs
import modules.innertube.models.ThumbnailRenderer
import modules.innertube.models.Thumbnails

data class BrowseResponse(
    @SerializedName("contents") val contents: Contents? = null,
    @SerializedName("continuationContents") val continuationContents: ContinuationContents? = null,
    @SerializedName("header") val header: Header? = null,
    @SerializedName("microformat") val microformat: Microformat? = null,
    @SerializedName("responseContext") val responseContext: ResponseContext,
    @SerializedName("background") val background: MusicThumbnailRenderer? = null
) {
    data class Contents(
        @SerializedName("singleColumnBrowseResultsRenderer") val singleColumnBrowseResultsRenderer: Tabs? = null,
        @SerializedName("twoColumnBrowseResultsRenderer") val twoColumnBrowseResultsRenderer: TwoColumnBrowseResultsRenderer? = null,
        @SerializedName("sectionListRenderer") val sectionListRenderer: SectionListRenderer? = null
    )

    data class TwoColumnBrowseResultsRenderer(
        @SerializedName("tabs") val tabs: List<Tabs.Tab?>? = null,
        @SerializedName("secondaryContents") val secondaryContents: SecondaryContents? = null
    )

    data class SecondaryContents(
        @SerializedName("sectionListRenderer") val sectionListRenderer: SectionListRenderer? = null
    )

    data class MusicThumbnailRenderer(
        @SerializedName("thumbnail") val thumbnail: Thumbnails? = null,
        @SerializedName("thumbnailCrop") val thumbnailCrop: String? = null
    )

    data class ContinuationContents(
        @SerializedName("sectionListContinuation") val sectionListContinuation: SectionListContinuation? = null,
        @SerializedName("musicPlaylistShelfContinuation") val musicPlaylistShelfContinuation: MusicPlaylistShelfContinuation? = null,
        @SerializedName("gridContinuation") val gridContinuation: GridContinuation? = null
    ) {
        data class SectionListContinuation(
            @SerializedName("contents") val contents: List<SectionListRenderer.Content>,
            @SerializedName("continuations") val continuations: List<Continuation>? = null
        )

        data class MusicPlaylistShelfContinuation(
            @SerializedName("contents") val contents: List<MusicShelfRenderer.Content>,
            @SerializedName("continuations") val continuations: List<Continuation>? = null
        )

        data class GridContinuation(
            @SerializedName("items") val items: List<GridRenderer.Item>,
            @SerializedName("continuations") val continuations: List<Continuation>? = null
        )
    }

    data class Header(
        @SerializedName("musicImmersiveHeaderRenderer") val musicImmersiveHeaderRenderer: MusicImmersiveHeaderRenderer? = null,
        @SerializedName("musicDetailHeaderRenderer") val musicDetailHeaderRenderer: MusicDetailHeaderRenderer? = null,
        @SerializedName("musicEditablePlaylistDetailHeaderRenderer") val musicEditablePlaylistDetailHeaderRenderer: MusicEditablePlaylistDetailHeaderRenderer? = null,
        @SerializedName("musicVisualHeaderRenderer") val musicVisualHeaderRenderer: MusicVisualHeaderRenderer? = null,
        @SerializedName("musicHeaderRenderer") val musicHeaderRenderer: MusicHeaderRenderer? = null
    ) {
        data class MusicImmersiveHeaderRenderer(
            @SerializedName("title") val title: Runs,
            @SerializedName("description") val description: Runs? = null,
            @SerializedName("thumbnail") val thumbnail: ThumbnailRenderer? = null,
            @SerializedName("playButton") val playButton: Button? = null,
            @SerializedName("startRadioButton") val startRadioButton: Button? = null,
            @SerializedName("menu") val menu: Menu
        )

        data class MusicDetailHeaderRenderer(
            @SerializedName("title") val title: Runs,
            @SerializedName("subtitle") val subtitle: Runs,
            @SerializedName("secondSubtitle") val secondSubtitle: Runs,
            @SerializedName("description") val description: Runs? = null,
            @SerializedName("thumbnail") val thumbnail: ThumbnailRenderer,
            @SerializedName("menu") val menu: Menu
        )

        data class MusicEditablePlaylistDetailHeaderRenderer(
            @SerializedName("header") val header: Header
        ) {
            data class Header(
                @SerializedName("musicDetailHeaderRenderer") val musicDetailHeaderRenderer: MusicDetailHeaderRenderer? = null,
                @SerializedName("musicResponsiveHeaderRenderer") val musicResponsiveHeaderRenderer: MusicHeaderRenderer? = null
            )
        }

        data class MusicVisualHeaderRenderer(
            @SerializedName("title") val title: Runs,
            @SerializedName("foregroundThumbnail") val foregroundThumbnail: ThumbnailRenderer,
            @SerializedName("thumbnail") val thumbnail: ThumbnailRenderer? = null
        )

        data class Buttons(
            @SerializedName("menuRenderer") val menuRenderer: Menu.MenuRenderer? = null
        )

        data class MusicHeaderRenderer(
            @SerializedName("buttons") val buttons: List<Buttons>? = null,
            @SerializedName("title") val title: Runs? = null,
            @SerializedName("thumbnail") val thumbnail: MusicThumbnailRenderer? = null,
            @SerializedName("subtitle") val subtitle: Runs? = null,
            @SerializedName("secondSubtitle") val secondSubtitle: Runs? = null,
            @SerializedName("straplineTextOne") val straplineTextOne: Runs? = null,
            @SerializedName("straplineThumbnail") val straplineThumbnail: MusicThumbnailRenderer? = null
        )

        data class MusicThumbnail(
            @SerializedName("url") val url: String? = null
        )

        data class MusicThumbnailRenderer(
            @SerializedName("musicThumbnailRenderer") val musicThumbnailRenderer: BrowseResponse.MusicThumbnailRenderer,
            @SerializedName("thumbnails") val thumbnails: List<MusicThumbnail>? = null
        )
    }

    data class Microformat(
        @SerializedName("microformatDataRenderer") val microformatDataRenderer: MicroformatDataRenderer? = null
    ) {
        data class MicroformatDataRenderer(
            @SerializedName("urlCanonical") val urlCanonical: String? = null
        )
    }
}