package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class NavigationEndpoint(
    @SerializedName("watchEndpoint") val watchEndpoint: WatchEndpoint? = null,
    @SerializedName("watchPlaylistEndpoint") val watchPlaylistEndpoint: WatchEndpoint? = null,
    @SerializedName("browseEndpoint") val browseEndpoint: BrowseEndpoint? = null,
    @SerializedName("searchEndpoint") val searchEndpoint: SearchEndpoint? = null,
    @SerializedName("queueAddEndpoint") val queueAddEndpoint: QueueAddEndpoint? = null,
    @SerializedName("shareEntityEndpoint") val shareEntityEndpoint: ShareEntityEndpoint? = null,
) {
    val endpoint: Endpoint?
        get() = watchEndpoint
            ?: watchPlaylistEndpoint
            ?: browseEndpoint
            ?: searchEndpoint
            ?: queueAddEndpoint
            ?: shareEntityEndpoint

    val anyWatchEndpoint: WatchEndpoint?
        get() = watchEndpoint
            ?: watchPlaylistEndpoint
}
