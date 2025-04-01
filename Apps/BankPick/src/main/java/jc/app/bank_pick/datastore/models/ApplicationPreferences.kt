package jc.app.bank_pick.datastore.models

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationPreferences(
    val firstLaunch: Boolean = true,
    val isLogin: Boolean = false,
)
