import com.google.gson.annotations.SerializedName

data class Badges(
    @SerializedName("musicInlineBadgeRenderer") val musicInlineBadgeRenderer: MusicInlineBadgeRenderer?,
) {
    data class MusicInlineBadgeRenderer(
        @SerializedName("icon") val icon: Icon,
    )
}
