package com.apps.youtube.api.domain.repository

import com.apps.youtube.api.data.models.VideoListResponse
import modules.common.failure.Failure
import modules.common.functional.Either

interface YoutubeApiRepository {
    suspend fun searchVideos(
        channelId: String, pageToken: String?
    ): Either<Failure, VideoListResponse>
}