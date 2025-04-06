package com.apps.youtube.api.data.repository

import com.apps.youtube.api.data.datasource.YouTubeApiService
import com.apps.youtube.api.data.models.CommentThreadListResponse
import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.failure.Failure.ServerError
import modules.common.functional.Either
import javax.inject.Inject

class YoutubeApiRepositoryImpl @Inject constructor(
    private val apiService: YouTubeApiService,
) : YoutubeApiRepository {
    override suspend fun getVideosWithDetails(
        channelId: String, pageToken: String?
    ): Either<Failure, VideoListResponse> {
        try {
            // Step 1: Fetch video IDs from search endpoint
            val searchResponse =
                apiService.searchVideos(channelId = channelId, pageToken = pageToken)

            if (!searchResponse.isSuccessful) {
                return Either.Left(ServerError(searchResponse.message()))
            }

            if (searchResponse.body() == null) {
                return Either.Left(ServerError("No data found"))
            }

            val videoIds = searchResponse.body()!!.items.map { it.id.videoId }
            if (videoIds.isEmpty()) {
                return Either.Left(ServerError("No related video IDs found"))
            }

            val nextPageToken = searchResponse.body()!!.nextPageToken

            // Step 2: Fetch video details
            val detailsResponse = apiService.getVideoDetails(id = videoIds.joinToString(","))
            if (!detailsResponse.isSuccessful) {
                return Either.Left(ServerError(detailsResponse.message()))
            }

            if (detailsResponse.body() == null) {
                return Either.Left(ServerError("No data found"))
            }

            if (detailsResponse.body()!!.items.isEmpty()) {
                Either.Left(ServerError("Failed to load video details"))
            }

            val videoDetails = detailsResponse.body()!!.copy(
                nextPageToken = nextPageToken,
            )

            return Either.Right(videoDetails)
        } catch (e: Exception) {
            return Either.Left(ServerError(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getComments(
        videoId: String, pageToken: String?
    ): Either<Failure, CommentThreadListResponse> {
        return try {
            val commentResponse = apiService.getComments(videoId = videoId, pageToken = pageToken)

            if (!commentResponse.isSuccessful) {
                return Either.Left(ServerError(commentResponse.message()))
            }

            if (commentResponse.body() == null) {
                return Either.Left(ServerError("No data found"))
            }

            if (commentResponse.body()!!.items.isEmpty()) {
                return Either.Left(ServerError("Failed to load comments or no comments found"))
            }

            Either.Right(commentResponse.body()!!)
        } catch (e: Exception) {
            return Either.Left(ServerError(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getRelatedVideosWithDetails(
        query: String, pageToken: String?
    ): Either<Failure, VideoListResponse> {
        try {
            // Step 1: Fetch related video IDs
            val searchResponse = apiService.searchVideosWithQuery(
                query = query,
                pageToken = pageToken,
            )

            if (!searchResponse.isSuccessful) {
                return Either.Left(ServerError(searchResponse.message()))
            }

            if (searchResponse.body() == null) {
                return Either.Left(ServerError("Failed to load related videos."))
            }

            // Extract video IDs and channel IDs
            val videoIds = searchResponse.body()!!.items.map { it.id.videoId }
            if (videoIds.isEmpty()) {
                return Either.Left(ServerError("No related video IDs found"))
            }

            val channelIds = searchResponse.body()!!.items.map { it.snippet.channelId }.toSet()
            if (channelIds.isEmpty()) {
                return Either.Left(ServerError("No channel IDs found"))
            }

            val nextPageToken = searchResponse.body()!!.nextPageToken

            // Step 2: Fetch video details
            val detailsResponse = apiService.getVideoDetails(id = videoIds.joinToString(","))

            if (!detailsResponse.isSuccessful) {
                return Either.Left(ServerError(searchResponse.message()))
            }

            if (detailsResponse.body() == null) {
                return Either.Left(ServerError("Failed to load video details"))
            }

            if (detailsResponse.body()!!.items.isEmpty()) {
                return Either.Left(ServerError("Failed to load video details"))
            }

            // Step 3: Fetch channel details
            val channelResponse = apiService.getChannelDetails(id = channelIds.joinToString(","))

            if (!channelResponse.isSuccessful) {
                return Either.Left(ServerError(searchResponse.message()))
            }

            if (channelResponse.body() == null) {
                return Either.Left(ServerError("Failed to load channel details"))
            }

//            // Create a map of channel details for quick lookup
//            val channelDetailsMap = channelResponse.body()!!.items.associateBy { it.id }
//            detailsResponse.body()!!.items.forEach { videoItem ->
//                val channelId = videoItem.snippet!!.channelId
//                if (channelId != null && channelDetailsMap.containsKey(channelId)) {
//                    videoItem.channelDetails = channelDetailsMap[channelId]
//                }
//            }

            detailsResponse.body()!!.nextPageToken = nextPageToken
            val videoDetails = detailsResponse.body()!!.copy(
                nextPageToken = nextPageToken,
            )

            return Either.Right(videoDetails)
        } catch (e: Exception) {
            return Either.Left(ServerError(e.message ?: "Unknown error"))
        }
    }
}