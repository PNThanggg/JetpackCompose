package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class Button(
    @SerializedName("buttonRenderer") val buttonRenderer: ButtonRenderer
) {
    data class ButtonRenderer(
        @SerializedName("text") val text: Runs,
        @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint? = null,
        @SerializedName("command") val command: NavigationEndpoint? = null,
        @SerializedName("icon") val icon: Icon? = null
    )
}