package com.apps.youtube.api.data.models

import com.google.gson.annotations.SerializedName

data class SubscriptionListResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("items") val listSubscription: MutableList<Subscription>,
) {
    data class PageInfo(
        @SerializedName("totalResults") val totalResults: Int,
        @SerializedName("resultsPerPage") val resultsPerPage: Int
    )

    data class Subscription(
        @SerializedName("kind") val kind: String,
        @SerializedName("etag") val etag: String,
        @SerializedName("snippet") val snippet: Snippet,
    ) {
        data class Snippet(
            @SerializedName("publishedAt") val publishedAt: String,
            @SerializedName("title") val title: String,
            @SerializedName("description") val description: String,
            @SerializedName("resourceId") val resourceId: ResourceId,
            @SerializedName("channelId") val channelId: String,
            @SerializedName("thumbnails") val thumbnails: Thumbnails,
        ) {
            data class ResourceId(
                @SerializedName("kind") val kind: String,
                @SerializedName("channelId") val channelId: String,
            )

            data class Thumbnails(
                @SerializedName("default") val default: Thumbnail,
                @SerializedName("medium") val medium: Thumbnail,
                @SerializedName("high") val high: Thumbnail,
            ) {
                data class Thumbnail(
                    @SerializedName("url") val url: String,
                )
            }
        }
    }
}