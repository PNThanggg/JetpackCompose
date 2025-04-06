package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

data class GetVideosWithDetailsParams(
    val channelId: String, val pageToken: String? = null
)

class GetVideosWithDetailsUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithParams<VideoListResponse, GetVideosWithDetailsParams>() {
    override suspend fun run(params: GetVideosWithDetailsParams): Either<Failure, VideoListResponse> {
        return apiRepository.getVideosWithDetails(
            channelId = params.channelId,
            pageToken = params.pageToken,
        )
    }
}