package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.CommentThreadListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

data class GetCommentsParams(
    val videoId: String,
    val pageToken: String? = null,
)

class GetCommentsUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithParams<CommentThreadListResponse, GetCommentsParams>() {
    override suspend fun run(params: GetCommentsParams): Either<Failure, CommentThreadListResponse> {
        return apiRepository.getComments(
            videoId = params.videoId, pageToken = params.pageToken
        )
    }
}