package modules.innertube.models.response

import com.google.gson.annotations.SerializedName

data class NextResponse(
    @SerializedName("contents") val contents: Contents,
    @SerializedName("continuationContents") val continuationContents: ContinuationContents? = null,
    @SerializedName("currentVideoEndpoint") val currentVideoEndpoint: NavigationEndpoint? = null
) {
    data class Contents(
        @SerializedName("singleColumnMusicWatchNextResultsRenderer") val singleColumnMusicWatchNextResultsRenderer: SingleColumnMusicWatchNextResultsRenderer
    ) {
        data class SingleColumnMusicWatchNextResultsRenderer(
            @SerializedName("tabbedRenderer") val tabbedRenderer: TabbedRenderer
        ) {
            data class TabbedRenderer(
                @SerializedName("watchNextTabbedResultsRenderer") val watchNextTabbedResultsRenderer: WatchNextTabbedResultsRenderer
            ) {
                data class WatchNextTabbedResultsRenderer(
                    @SerializedName("tabs") val tabs: List<Tabs.Tab>
                )
            }
        }
    }

    data class ContinuationContents(
        @SerializedName("playlistPanelContinuation") val playlistPanelContinuation: PlaylistPanelRenderer
    )
}