package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class Context(
    @SerializedName("client") val client: Client,
    @SerializedName("thirdParty") val thirdParty: ThirdParty? = null,
) {
    data class Client(
        @SerializedName("clientName") val clientName: String,
        @SerializedName("clientVersion") val clientVersion: String,
        @SerializedName("osVersion") val osVersion: String?,
        @SerializedName("gl") val gl: String,
        @SerializedName("hl") val hl: String,
        @SerializedName("visitorData") val visitorData: String?,
    )

    data class ThirdParty(
        @SerializedName("embedUrl") val embedUrl: String,
    )
}
