package com.apps.youtube.api.data.models

import com.google.gson.annotations.SerializedName

data class Channel(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("localizedTitle") val localizedTitle: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("localizedDescription") val localizedDescription: String? = null,
    @SerializedName("avatar") val avatar: String? = null,
    @SerializedName("bannerImageUrl") val bannerImageUrl: String? = null,
    @SerializedName("subscriberCount") val subscriberCount: String? = null,
    @SerializedName("videoCount") val videoCount: String? = null,
    @SerializedName("viewCount") val viewCount: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("customUrl") val customUrl: String? = null
)