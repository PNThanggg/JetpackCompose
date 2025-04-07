package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class MusicPlaylistShelfRenderer(
    @SerializedName("playlistId") val playlistId: String? = null,
    @SerializedName("contents") val contents: List<MusicShelfRenderer.Content>,
    @SerializedName("collapsedItemCount") val collapsedItemCount: Int,
    @SerializedName("continuations") val continuations: List<Continuation>? = null
)