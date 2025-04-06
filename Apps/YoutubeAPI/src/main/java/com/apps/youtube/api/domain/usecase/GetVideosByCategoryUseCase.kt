package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

data class GetVideosByCategoryParams(
    val categoryId: String, val pageToken: String? = null
)

class GetVideosByCategoryUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithParams<VideoListResponse, GetVideosByCategoryParams>() {
    override suspend fun run(
        params: GetVideosByCategoryParams
    ): Either<Failure, VideoListResponse> {
        return apiRepository.getVideosByCategory(
            categoryId = params.categoryId, pageToken = params.pageToken,
        )
    }
}