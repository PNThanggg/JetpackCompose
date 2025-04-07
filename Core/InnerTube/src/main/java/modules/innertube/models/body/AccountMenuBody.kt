package modules.innertube.models.body

import com.google.gson.annotations.SerializedName
import modules.innertube.models.Context

data class AccountMenuBody(
    @SerializedName("context") val context: Context,
    @SerializedName("deviceTheme") val deviceTheme: String = "DEVICE_THEME_SELECTED",
    @SerializedName("userInterfaceTheme") val userInterfaceTheme: String = "USER_INTERFACE_THEME_DARK",
)
