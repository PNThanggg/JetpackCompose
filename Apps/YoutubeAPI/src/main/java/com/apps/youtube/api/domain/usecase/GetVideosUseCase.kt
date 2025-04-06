package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

data class GetVideoParams(
    val channelId: String, val pageToken: String? = null
)

class GetVideosUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithParams<VideoListResponse, GetVideoParams>() {
    override suspend fun run(params: GetVideoParams): Either<Failure, VideoListResponse> {
        return apiRepository.getVideos(
            params.channelId, params.pageToken
        )
    }
}