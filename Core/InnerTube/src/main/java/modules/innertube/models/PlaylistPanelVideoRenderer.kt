package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class PlaylistPanelVideoRenderer(
    @SerializedName("title") val title: Runs? = null,
    @SerializedName("lengthText") val lengthText: Runs? = null,
    @SerializedName("longBylineText") val longBylineText: Runs? = null,
    @SerializedName("shortBylineText") val shortBylineText: Runs? = null,
    @SerializedName("badges") val badges: List<Badges>? = null,
    @SerializedName("videoId") val videoId: String? = null,
    @SerializedName("playlistSetVideoId") val playlistSetVideoId: String? = null,
    @SerializedName("selected") val selected: Boolean,
    @SerializedName("thumbnail") val thumbnail: Thumbnails,
    @SerializedName("unplayableText") val unplayableText: Runs? = null,
    @SerializedName("menu") val menu: Menu? = null,
    @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint
)