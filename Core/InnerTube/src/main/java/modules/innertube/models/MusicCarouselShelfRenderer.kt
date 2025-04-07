package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class MusicCarouselShelfRenderer(
    @SerializedName("header") val header: Header? = null,
    @SerializedName("contents") val contents: List<Content>,
    @SerializedName("itemSize") val itemSize: String,
    @SerializedName("numItemsPerColumn") val numItemsPerColumn: Int? = null
) {
    data class Header(
        @SerializedName("musicCarouselShelfBasicHeaderRenderer") val musicCarouselShelfBasicHeaderRenderer: MusicCarouselShelfBasicHeaderRenderer
    ) {
        data class MusicCarouselShelfBasicHeaderRenderer(
            @SerializedName("strapline") val strapline: Runs? = null,
            @SerializedName("title") val title: Runs,
            @SerializedName("thumbnail") val thumbnail: ThumbnailRenderer? = null,
            @SerializedName("moreContentButton") val moreContentButton: Button? = null
        )
    }

    data class Content(
        @SerializedName("musicTwoRowItemRenderer") val musicTwoRowItemRenderer: MusicTwoRowItemRenderer? = null,
        @SerializedName("musicResponsiveListItemRenderer") val musicResponsiveListItemRenderer: MusicResponsiveListItemRenderer? = null,
        @SerializedName("musicNavigationButtonRenderer") val musicNavigationButtonRenderer: MusicNavigationButtonRenderer? = null // navigation button in explore tab
    )
}