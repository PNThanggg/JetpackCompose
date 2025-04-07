package modules.innertube.models

import com.google.gson.annotations.SerializedName
import modules.innertube.models.response.BrowseResponse

data class SectionListRenderer(
    @SerializedName("header") val header: Header? = null,
    @SerializedName("contents") val contents: List<Content>? = null,
    @SerializedName("continuations") val continuations: List<Continuation>? = null
) {
    data class Header(
        @SerializedName("chipCloudRenderer") val chipCloudRenderer: ChipCloudRenderer? = null
    ) {
        data class ChipCloudRenderer(
            @SerializedName("chips") val chips: List<Chip>
        ) {
            data class Chip(
                @SerializedName("chipCloudChipRenderer") val chipCloudChipRenderer: ChipCloudChipRenderer
            ) {
                data class ChipCloudChipRenderer(
                    @SerializedName("isSelected") val isSelected: Boolean,
                    @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint,
                    @SerializedName("text") val text: Runs? = null,
                    @SerializedName("uniqueId") val uniqueId: String? = null
                )
            }
        }
    }

    data class Content(
        @SerializedName("musicImmersiveCarouselShelfRenderer") val musicCarouselShelfRenderer: MusicCarouselShelfRenderer? = null,
        @SerializedName("musicShelfRenderer") val musicShelfRenderer: MusicShelfRenderer? = null,
        @SerializedName("musicCardShelfRenderer") val musicCardShelfRenderer: MusicCardShelfRenderer? = null,
        @SerializedName("musicPlaylistShelfRenderer") val musicPlaylistShelfRenderer: MusicPlaylistShelfRenderer? = null,
        @SerializedName("musicDescriptionShelfRenderer") val musicDescriptionShelfRenderer: MusicDescriptionShelfRenderer? = null,
        @SerializedName("gridRenderer") val gridRenderer: GridRenderer? = null,
        @SerializedName("musicResponsiveHeaderRenderer") val musicResponsiveHeaderRenderer: BrowseResponse.Header.MusicHeaderRenderer? = null,
        @SerializedName("musicEditablePlaylistDetailHeaderRenderer") val musicEditablePlaylistDetailHeaderRenderer: BrowseResponse.Header.MusicEditablePlaylistDetailHeaderRenderer? = null
    )
}