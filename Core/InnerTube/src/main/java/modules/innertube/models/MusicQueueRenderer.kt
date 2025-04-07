package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class MusicQueueRenderer(
    @SerializedName("content") val content: Content?,
    @SerializedName("header") val header: Header?,
) {
    data class Content(
        @SerializedName("playlistPanelRenderer") val playlistPanelRenderer: PlaylistPanelRenderer,
    )

    data class Header(
        @SerializedName("musicQueueHeaderRenderer") val musicQueueHeaderRenderer: MusicQueueHeaderRenderer?,
    ) {
        data class MusicQueueHeaderRenderer(
            @SerializedName("title") val title: Runs?,
            @SerializedName("subtitle") val subtitle: Runs?,
        )
    }
}
