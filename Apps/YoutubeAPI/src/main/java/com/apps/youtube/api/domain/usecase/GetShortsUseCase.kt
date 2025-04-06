package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

data class GetShortsParams(
    val query: String, val pageToken: String? = null
)

class GetShortsUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithParams<VideoListResponse, GetShortsParams>() {
    override suspend fun run(params: GetShortsParams): Either<Failure, VideoListResponse> {
        return apiRepository.getShorts(
            query = params.query,
            pageToken = params.pageToken
        )
    }
}