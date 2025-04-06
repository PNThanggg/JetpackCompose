package modules.lib.lyrics.domain.models

import com.google.gson.annotations.SerializedName
import kotlin.math.abs

data class Track(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("trackName") val trackName: String,
    @SerializedName("artistName") val artistName: String,
    @SerializedName("albumName") val albumName: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("instrumental") val instrumental: Boolean,
    @SerializedName("plainLyrics") val plainLyrics: String?,
    @SerializedName("syncedLyrics") val syncedLyrics: String?,
)

internal fun List<Track>.bestMatchingFor(duration: Int) =
    firstOrNull { abs(it.duration.toInt() - duration) <= 2 }
