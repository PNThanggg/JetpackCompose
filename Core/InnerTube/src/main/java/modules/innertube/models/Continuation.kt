package modules.innertube.models

import com.google.gson.annotations.SerializedName

data class Continuation(
    @SerializedName("nextContinuationData") val nextContinuationData: NextContinuationData? = null,
    @SerializedName("nextRadioContinuationData") val nextRadioContinuationData: NextContinuationData? = null,
) {
    val effectiveContinuationData: NextContinuationData?
        get() = nextContinuationData ?: nextRadioContinuationData

    data class NextContinuationData(
        @SerializedName("continuation") val continuation: String,
    )
}