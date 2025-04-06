package com.apps.youtube.api.data.models

import com.google.gson.annotations.SerializedName

data class SearchResponseModel(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("nextPageToken") val nextPageToken: String,
    @SerializedName("regionCode") val regionCode: String,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("items") val items: List<Item>,
) {
    companion object {
        data class Item(
            @SerializedName("kind") val kind: String,
            @SerializedName("etag") val etag: String,
            @SerializedName("id") val id: Id,
            @SerializedName("snippet") val snippet: Snippet
        )

        data class Snippet(
            @SerializedName("publishedAt") val publishedAt: String,
            @SerializedName("channelId") val channelId: String,
            @SerializedName("title") val title: String,
            @SerializedName("description") val description: String,
            @SerializedName("thumbnails") val thumbnails: Thumbnails,
            @SerializedName("channelTitle") val channelTitle: String,
            @SerializedName("liveBroadcastContent") val liveBroadcastContent: String,
            @SerializedName("publishTime") val publishTime: String,
        )

        data class Thumbnails(
            @SerializedName("default") val default: Thumbnail,
            @SerializedName("medium") val medium: Thumbnail,
            @SerializedName("high") val high: Thumbnail,
        )

        data class Id(
            @SerializedName("kind") val kind: String, @SerializedName("videoId") val videoId: String
        )
    }
}