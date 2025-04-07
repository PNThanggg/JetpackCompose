package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class GridRenderer(
    @SerializedName("header") val header: Header? = null,
    @SerializedName("items") val items: List<Item>,
    @SerializedName("continuations") val continuations: List<Continuation>? = null
) {
    data class Header(
        @SerializedName("gridHeaderRenderer") val gridHeaderRenderer: GridHeaderRenderer
    ) {
        data class GridHeaderRenderer(
            @SerializedName("title") val title: Runs
        )
    }

    data class Item(
        @SerializedName("musicNavigationButtonRenderer") val musicNavigationButtonRenderer: MusicNavigationButtonRenderer? = null,
        @SerializedName("musicTwoRowItemRenderer") val musicTwoRowItemRenderer: MusicTwoRowItemRenderer? = null
    )
}