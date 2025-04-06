package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

data class GetRelatedVideosWithDetailsParams(
    val query: String, val pageToken: String? = null
)

class GetRelatedVideosWithDetailsUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithParams<VideoListResponse, GetRelatedVideosWithDetailsParams>() {
    override suspend fun run(
        params: GetRelatedVideosWithDetailsParams
    ): Either<Failure, VideoListResponse> {
        return apiRepository.getRelatedVideosWithDetails(
            query = params.query, pageToken = params.pageToken
        )
    }
}