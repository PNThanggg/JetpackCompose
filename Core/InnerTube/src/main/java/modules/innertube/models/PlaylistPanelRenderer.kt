package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class PlaylistPanelRenderer(
    @SerializedName("title") val title: String?,
    @SerializedName("titleText") val titleText: Runs?,
    @SerializedName("shortBylineText") val shortBylineText: Runs?,
    @SerializedName("contents") val contents: List<Content>,
    @SerializedName("isInfinite") val isInfinite: Boolean,
    @SerializedName("numItemsToShow") val numItemsToShow: Int?,
    @SerializedName("playlistId") val playlistId: String?,
    @SerializedName("continuations") val continuations: List<Continuation>?,
) {
    data class Content(
        @SerializedName("playlistPanelVideoRenderer") val playlistPanelVideoRenderer: PlaylistPanelVideoRenderer?,
        @SerializedName("automixPreviewVideoRenderer") val automixPreviewVideoRenderer: AutomixPreviewVideoRenderer?,
    )
}
