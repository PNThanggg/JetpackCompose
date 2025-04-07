package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class AccountInfo(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String?,
    @SerializedName("channelHandle") val channelHandle: String?,
)
