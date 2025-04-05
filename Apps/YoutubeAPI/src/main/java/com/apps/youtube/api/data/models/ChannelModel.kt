package com.apps.youtube.api.data.models

import com.google.gson.annotations.SerializedName

data class ChannelListResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("items") val items: List<Channel>
) {
    companion object {
        data class PageInfo(
            @SerializedName("totalResults") val totalResults: Int,
            @SerializedName("resultsPerPage") val resultsPerPage: Int
        )

        data class Channel(
            @SerializedName("kind") val kind: String,
            @SerializedName("etag") val etag: String,
            @SerializedName("id") val id: String,
            @SerializedName("snippet") val snippet: Snippet,
            @SerializedName("statistics") val statistics: Statistics
        )

        data class Snippet(
            @SerializedName("title") val title: String,
            @SerializedName("description") val description: String,
            @SerializedName("customUrl") val customUrl: String,
            @SerializedName("publishedAt") val publishedAt: String,
            @SerializedName("thumbnails") val thumbnails: Thumbnails,
            @SerializedName("localized") val localized: Localized,
            @SerializedName("country") val country: String
        )

        data class Thumbnails(
            @SerializedName("default") val defaultThumb: ThumbnailInfo,
            @SerializedName("medium") val medium: ThumbnailInfo,
            @SerializedName("high") val high: ThumbnailInfo
        )

        data class ThumbnailInfo(
            @SerializedName("url") val url: String,
            @SerializedName("width") val width: Int,
            @SerializedName("height") val height: Int
        )

        data class Localized(
            @SerializedName("title") val title: String,
            @SerializedName("description") val description: String
        )

        data class Statistics(
            @SerializedName("viewCount") val viewCount: String,
            @SerializedName("subscriberCount") val subscriberCount: String,
            @SerializedName("hiddenSubscriberCount") val hiddenSubscriberCount: Boolean,
            @SerializedName("videoCount") val videoCount: String
        )
    }
}


