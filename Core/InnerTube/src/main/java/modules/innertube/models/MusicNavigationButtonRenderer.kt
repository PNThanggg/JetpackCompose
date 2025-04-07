package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class MusicNavigationButtonRenderer(
    @SerializedName("buttonText") val buttonText: Runs,
    @SerializedName("solid") val solid: Solid? = null,
    @SerializedName("iconStyle") val iconStyle: IconStyle? = null,
    @SerializedName("clickCommand") val clickCommand: NavigationEndpoint
) {
    data class Solid(
        @SerializedName("leftStripeColor") val leftStripeColor: Long
    )

    data class IconStyle(
        @SerializedName("icon") val icon: Icon
    )
}
