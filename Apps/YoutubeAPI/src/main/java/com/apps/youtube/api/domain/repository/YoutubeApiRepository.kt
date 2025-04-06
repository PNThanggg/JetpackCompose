package com.apps.youtube.api.domain.repository

import com.apps.youtube.api.data.models.CommentThreadListResponse
import com.apps.youtube.api.data.models.VideoListResponse
import modules.common.failure.Failure
import modules.common.functional.Either

interface YoutubeApiRepository {
    suspend fun getVideosWithDetails(
        channelId: String, pageToken: String?
    ): Either<Failure, VideoListResponse>

    suspend fun getComments(
        videoId: String, pageToken: String?
    ): Either<Failure, CommentThreadListResponse>

    suspend fun getRelatedVideosWithDetails(
        query: String,
        pageToken: String?,
    ): Either<Failure, VideoListResponse>
}