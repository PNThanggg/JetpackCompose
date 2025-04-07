package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class Tabs(
    @SerializedName("tabs") val tabs: List<Tab>,
) {
    data class Tab(
        @SerializedName("tabRenderer") val tabRenderer: TabRenderer,
    ) {
        data class TabRenderer(
            @SerializedName("title") val title: String?,
            @SerializedName("content") val content: Content?,
            @SerializedName("endpoint") val endpoint: NavigationEndpoint?,
        ) {
            data class Content(
                @SerializedName("sectionListRenderer") val sectionListRenderer: SectionListRenderer?,
                @SerializedName("musicQueueRenderer") val musicQueueRenderer: MusicQueueRenderer?,
            )
        }
    }
}
