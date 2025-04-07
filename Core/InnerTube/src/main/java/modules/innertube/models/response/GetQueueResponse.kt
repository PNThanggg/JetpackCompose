package modules.innertube.models.response

import com.google.gson.annotations.SerializedName
import modules.innertube.models.PlaylistPanelRenderer

data class GetQueueResponse(
    @SerializedName("queueDatas") val queueDatas: List<QueueData>,
) {
    data class QueueData(
        @SerializedName("content") val content: PlaylistPanelRenderer.Content,
    )
}
