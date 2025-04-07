package modules.innertube.models.response

import com.google.gson.annotations.SerializedName
import modules.innertube.models.SearchSuggestionsSectionRenderer

data class GetSearchSuggestionsResponse(
    @SerializedName("contents") val contents: List<Content>?,
) {
    data class Content(
        @SerializedName("searchSuggestionsSectionRenderer") val searchSuggestionsSectionRenderer: SearchSuggestionsSectionRenderer,
    )
}
