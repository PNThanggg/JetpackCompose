package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.ChannelListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

data class GetMyChannelDetailParam(
    val accessToken: String, val maxResults: Int? = null, val pageToken: String? = null,
)

class GetMyChannelDetailUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithParams<ChannelListResponse, GetMyChannelDetailParam>() {
    override suspend fun run(params: GetMyChannelDetailParam): Either<Failure, ChannelListResponse> {
        return apiRepository.getMyChannelDetail(
            accessToken = "Bearer ${params.accessToken}",
            maxResults = params.maxResults,
            pageToken = params.pageToken
        )
    }
}