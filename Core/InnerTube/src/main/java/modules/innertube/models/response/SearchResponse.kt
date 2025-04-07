package modules.innertube.models.response

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Continuation
import modules.innertube.models.MusicResponsiveListItemRenderer
import modules.innertube.models.Tabs

data class SearchResponse(
    @SerializedName("contents") val contents: Contents?,
    @SerializedName("continuationContents") val continuationContents: ContinuationContents?,
) {
    data class Contents(
        @SerializedName("tabbedSearchResultsRenderer") val tabbedSearchResultsRenderer: Tabs?,
    )

    data class ContinuationContents(
        @SerializedName("musicShelfContinuation") val musicShelfContinuation: MusicShelfContinuation,
    ) {
        data class MusicShelfContinuation(
            @SerializedName("contents") val contents: List<Content>,
            @SerializedName("continuations") val continuations: List<Continuation>?,
        ) {
            data class Content(
                @SerializedName("musicResponsiveListItemRenderer") val musicResponsiveListItemRenderer: MusicResponsiveListItemRenderer,
            )
        }
    }
}
