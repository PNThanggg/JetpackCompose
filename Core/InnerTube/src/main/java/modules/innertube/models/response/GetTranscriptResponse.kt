package modules.innertube.models.response

import com.google.gson.annotations.SerializedName

data class GetTranscriptResponse(
    @SerializedName("actions") val actions: List<Action>? = null
) {
    data class Action(
        @SerializedName("updateEngagementPanelAction") val updateEngagementPanelAction: UpdateEngagementPanelAction
    ) {
        data class UpdateEngagementPanelAction(
            @SerializedName("content") val content: Content
        ) {
            data class Content(
                @SerializedName("transcriptRenderer") val transcriptRenderer: TranscriptRenderer
            ) {
                data class TranscriptRenderer(
                    @SerializedName("body") val body: Body
                ) {
                    data class Body(
                        @SerializedName("transcriptBodyRenderer") val transcriptBodyRenderer: TranscriptBodyRenderer
                    ) {
                        data class TranscriptBodyRenderer(
                            @SerializedName("cueGroups") val cueGroups: List<CueGroup>
                        ) {
                            data class CueGroup(
                                @SerializedName("transcriptCueGroupRenderer") val transcriptCueGroupRenderer: TranscriptCueGroupRenderer
                            ) {
                                data class TranscriptCueGroupRenderer(
                                    @SerializedName("cues") val cues: List<Cue>
                                ) {
                                    data class Cue(
                                        @SerializedName("transcriptCueRenderer") val transcriptCueRenderer: TranscriptCueRenderer
                                    ) {
                                        data class TranscriptCueRenderer(
                                            @SerializedName("cue") val cue: SimpleText,
                                            @SerializedName("startOffsetMs") val startOffsetMs: Long,
                                            @SerializedName("durationMs") val durationMs: Long
                                        ) {
                                            data class SimpleText(
                                                @SerializedName("simpleText") val simpleText: String
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}