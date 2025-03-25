package modules.spotify.network.entity

import com.google.gson.annotations.SerializedName

data class GetApiTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("expires_in")
    val expiresIn: Long,
)
