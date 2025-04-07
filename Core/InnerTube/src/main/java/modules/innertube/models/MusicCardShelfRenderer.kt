package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class MusicCardShelfRenderer(
    @SerializedName("title") val title: Runs,
    @SerializedName("subtitle") val subtitle: Runs,
    @SerializedName("thumbnail") val thumbnail: ThumbnailRenderer,
    @SerializedName("header") val header: Header,
    @SerializedName("contents") val contents: List<Content>? = null,
    @SerializedName("buttons") val buttons: List<Button>,
    @SerializedName("onTap") val onTap: NavigationEndpoint,
    @SerializedName("subtitleBadges") val subtitleBadges: List<Badges>? = null
) {
    data class Header(
        @SerializedName("musicCardShelfHeaderBasicRenderer") val musicCardShelfHeaderBasicRenderer: MusicCardShelfHeaderBasicRenderer
    ) {
        data class MusicCardShelfHeaderBasicRenderer(
            @SerializedName("title") val title: Runs
        )
    }

    data class Content(
        @SerializedName("musicResponsiveListItemRenderer") val musicResponsiveListItemRenderer: MusicResponsiveListItemRenderer? = null
    )
}