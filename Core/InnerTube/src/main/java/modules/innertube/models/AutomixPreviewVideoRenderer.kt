package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class AutomixPreviewVideoRenderer(
    @SerializedName("content") val content: Content,
) {
    data class Content(
        @SerializedName("automixPlaylistVideoRenderer") val automixPlaylistVideoRenderer: AutomixPlaylistVideoRenderer,
    ) {
        data class AutomixPlaylistVideoRenderer(
            @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint,
        )
    }
}
