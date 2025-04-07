package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class SearchSuggestionsSectionRenderer(
    @SerializedName("contents") val contents: List<Content>
) {
    data class Content(
        @SerializedName("searchSuggestionRenderer") val searchSuggestionRenderer: SearchSuggestionRenderer? = null,
        @SerializedName("musicResponsiveListItemRenderer") val musicResponsiveListItemRenderer: MusicResponsiveListItemRenderer? = null
    ) {
        data class SearchSuggestionRenderer(
            @SerializedName("suggestion") val suggestion: Runs,
            @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint
        )
    }
}
