package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("menuRenderer") val menuRenderer: MenuRenderer,
) {
    data class MenuRenderer(
        @SerializedName("items") val items: List<Item>?,
        @SerializedName("topLevelButtons") val topLevelButtons: List<TopLevelButton>?,
    ) {
        data class Item(
            @SerializedName("menuNavigationItemRenderer") val menuNavigationItemRenderer: MenuNavigationItemRenderer?,
            @SerializedName("menuServiceItemRenderer") val menuServiceItemRenderer: MenuServiceItemRenderer?,
        ) {
            data class MenuNavigationItemRenderer(
                @SerializedName("text") val text: Runs,
                @SerializedName("icon") val icon: Icon,
                @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint,
            )

            data class MenuServiceItemRenderer(
                @SerializedName("text") val text: Runs,
                @SerializedName("icon") val icon: Icon,
                @SerializedName("serviceEndpoint") val serviceEndpoint: NavigationEndpoint,
            )
        }

        data class TopLevelButton(
            @SerializedName("buttonRenderer") val buttonRenderer: ButtonRenderer?,
        ) {
            data class ButtonRenderer(
                @SerializedName("icon") val icon: Icon,
                @SerializedName("navigationEndpoint") val navigationEndpoint: NavigationEndpoint,
            )
        }
    }
}
