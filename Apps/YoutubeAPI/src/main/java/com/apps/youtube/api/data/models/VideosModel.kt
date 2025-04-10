package com.apps.youtube.api.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class VideoListResponse(
    @SerializedName("kind") val kind: String?,
    @SerializedName("etag") val etag: String?,
    @SerializedName("nextPageToken") val nextPageToken: String?,
    @SerializedName("prevPageToken") val prevPageToken: String?,
    @SerializedName("pageInfo") val pageInfo: PageInfo?,
    @SerializedName("items") val items: MutableList<Video> = mutableListOf(),
) {
    companion object {
        data class Video(
            @SerializedName("kind") val kind: String?,
            @SerializedName("etag") val etag: String?,
            @SerializedName("id") val id: String?,
            @SerializedName("snippet") val snippet: Snippet?,
            @SerializedName("contentDetails") val contentDetails: ContentDetails?,
            @SerializedName("statistics") val statistics: Statistics?,
            @SerializedName("channelDetails") var channelDetails: ChannelDetails?
        )

        data class Snippet(
            @SerializedName("publishedAt") val publishedAt: Date?,
            @SerializedName("channelId") val channelId: String?,
            @SerializedName("title") val title: String?,
            @SerializedName("description") val description: String?,
            @SerializedName("thumbnails") val thumbnails: Thumbnails?,
            @SerializedName("channelTitle") val channelTitle: String?,
            @SerializedName("tags") val tags: List<String>?,
            @SerializedName("categoryId") val categoryId: String?,
            @SerializedName("liveBroadcastContent") val liveBroadcastContent: String?,
            @SerializedName("localized") val localized: Localized?
        )

        data class Thumbnails(
            @SerializedName("default") val defaultThumbnail: Thumbnail?,
            @SerializedName("medium") val medium: Thumbnail?,
            @SerializedName("high") val high: Thumbnail?,
            @SerializedName("standard") val standard: Thumbnail?,
            @SerializedName("maxres") val maxres: Thumbnail?
        )

        data class Localized(
            @SerializedName("title") val title: String?,
            @SerializedName("description") val description: String?
        )

        data class ContentDetails(
            @SerializedName("duration") val duration: String?,
            @SerializedName("dimension") val dimension: String?,
            @SerializedName("definition") val definition: String?,
            @SerializedName("caption") val caption: String?,
            @SerializedName("licensedContent") val licensedContent: Boolean?,
            @SerializedName("projection") val projection: String?
        )

        data class Statistics(
            @SerializedName("viewCount") val viewCount: String?,
            @SerializedName("likeCount") val likeCount: String?,
            @SerializedName("favoriteCount") val favoriteCount: String?,
            @SerializedName("commentCount") val commentCount: String?
        )

        data class ChannelDetails(
            @SerializedName("id") val id: String?,
            @SerializedName("snippet") val snippet: ChannelSnippet?,
            @SerializedName("statistics") val statistics: ChannelStatistics?
        )

        data class ChannelSnippet(
            @SerializedName("title") val title: String?,
            @SerializedName("description") val description: String?,
            @SerializedName("customUrl") val customUrl: String?,
            @SerializedName("thumbnails") val thumbnails: Thumbnails?
        )

        data class ChannelStatistics(
            @SerializedName("subscriberCount") val subscriberCount: String?,
            @SerializedName("videoCount") val videoCount: String?,
            @SerializedName("viewCount") val viewCount: String?
        )
    }
}
