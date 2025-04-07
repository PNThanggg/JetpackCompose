package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class MusicShelfRenderer(
    @SerializedName("title") val title: Runs?,
    @SerializedName("contents") val contents: List<Content>?,
    @SerializedName("bottomEndpoint") val bottomEndpoint: NavigationEndpoint?,
    @SerializedName("moreContentButton") val moreContentButton: Button?,
    @SerializedName("continuations") val continuations: List<Continuation>?,
) {
    data class Content(
        @SerializedName("musicResponsiveListItemRenderer") val musicResponsiveListItemRenderer: MusicResponsiveListItemRenderer,
    )
}

fun List<Continuation>.getContinuation() = firstOrNull()?.nextContinuationData?.continuation
