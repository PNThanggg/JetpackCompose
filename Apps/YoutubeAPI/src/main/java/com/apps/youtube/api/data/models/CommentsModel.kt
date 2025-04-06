package com.apps.youtube.api.data.models

import com.google.gson.annotations.SerializedName

data class CommentThreadListResponse(
    @SerializedName("kind") val kind: String,
    @SerializedName("etag") val etag: String,
    @SerializedName("nextPageToken") val nextPageToken: String?,
    @SerializedName("pageInfo") val pageInfo: PageInfo,
    @SerializedName("items") val items: List<CommentThread>
) {
    companion object {
        data class CommentThread(
            @SerializedName("kind") val kind: String,
            @SerializedName("etag") val etag: String,
            @SerializedName("id") val id: String,
            @SerializedName("snippet") val snippet: Snippet,
            @SerializedName("replies") val replies: Replies?
        )

        data class Snippet(
            @SerializedName("channelId") val channelId: String,
            @SerializedName("videoId") val videoId: String,
            @SerializedName("topLevelComment") val topLevelComment: TopLevelComment,
            @SerializedName("canReply") val canReply: Boolean,
            @SerializedName("totalReplyCount") val totalReplyCount: Int,
            @SerializedName("isPublic") val isPublic: Boolean
        )

        data class TopLevelComment(
            @SerializedName("kind") val kind: String,
            @SerializedName("etag") val etag: String,
            @SerializedName("id") val id: String,
            @SerializedName("snippet") val snippet: SnippetDetail
        )

        data class SnippetDetail(
            @SerializedName("channelId") val channelId: String,
            @SerializedName("videoId") val videoId: String,
            @SerializedName("textDisplay") val textDisplay: String,
            @SerializedName("textOriginal") val textOriginal: String,
            @SerializedName("authorDisplayName") val authorDisplayName: String,
            @SerializedName("authorProfileImageUrl") val authorProfileImageUrl: String,
            @SerializedName("authorChannelUrl") val authorChannelUrl: String,
            @SerializedName("authorChannelId") val authorChannelId: AuthorChannelId,
            @SerializedName("canRate") val canRate: Boolean,
            @SerializedName("viewerRating") val viewerRating: String,
            @SerializedName("likeCount") val likeCount: Int,
            @SerializedName("publishedAt") val publishedAt: String,
            @SerializedName("updatedAt") val updatedAt: String
        )

        data class AuthorChannelId(
            @SerializedName("value") val value: String
        )

        data class Replies(
            @SerializedName("comments") val comments: List<TopLevelComment>
        )
    }
}
