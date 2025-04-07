package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class ResponseContext(
    @SerializedName("visitorData") val visitorData: String? = null,
    @SerializedName("serviceTrackingParams") val serviceTrackingParams: List<ServiceTrackingParam>? = null
) {
    data class ServiceTrackingParam(
        @SerializedName("params") val params: List<Param>,
        @SerializedName("service") val service: String
    ) {
        data class Param(
            @SerializedName("key") val key: String, @SerializedName("value") val value: String
        )
    }
}