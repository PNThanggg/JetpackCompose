package com.apps.youtube.api.data.repository

import com.apps.youtube.api.data.datasource.YouTubeApiService
import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.failure.Failure.ServerError
import modules.common.functional.Either
import javax.inject.Inject

class YoutubeApiRepositoryImpl @Inject constructor(
    private val apiService: YouTubeApiService,
) : YoutubeApiRepository {
    override suspend fun searchVideos(
        channelId: String, pageToken: String?
    ): Either<Failure, VideoListResponse> {
        return try {
            val response = apiService.searchVideos(
                channelId = channelId,
                pageToken = pageToken,
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Either.Right(body)
                } else {
                    Either.Left(ServerError("No data found"))
                }
            } else {
                Either.Left(ServerError(response.message()))
            }
        } catch (e: Exception) {
            Either.Left(ServerError(e.message ?: "Unknown error"))
        }
    }
}