package modules.innertube.models.response

import com.google.gson.annotations.SerializedName
import modules.innertube.models.AccountInfo
import modules.innertube.models.Runs

data class AccountMenuResponse(
    @SerializedName("actions") val actions: List<Action>,
) {
    data class Action(
        @SerializedName("openPopupAction") val openPopupAction: OpenPopupAction,
    ) {
        data class OpenPopupAction(
            @SerializedName("popup") val popup: Popup,
        ) {
            data class Popup(
                @SerializedName("multiPageMenuRenderer") val multiPageMenuRenderer: MultiPageMenuRenderer,
            ) {
                data class MultiPageMenuRenderer(
                    @SerializedName("header") val header: Header?,
                ) {
                    data class Header(
                        @SerializedName("activeAccountHeaderRenderer") val activeAccountHeaderRenderer: ActiveAccountHeaderRenderer,
                    ) {
                        data class ActiveAccountHeaderRenderer(
                            @SerializedName("accountName") val accountName: Runs,
                            @SerializedName("email") val email: Runs?,
                            @SerializedName("channelHandle") val channelHandle: Runs?,
                        ) {
                            fun toAccountInfo() = AccountInfo(
                                name = accountName.runs!!.first().text,
                                email = email?.runs?.first()?.text,
                                channelHandle = channelHandle?.runs?.first()?.text
                            )
                        }
                    }
                }
            }
        }
    }
}
