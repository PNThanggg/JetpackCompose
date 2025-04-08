package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.SubscriptionListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

class GetSubscriptionsUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithParams<SubscriptionListResponse, String>() {
    override suspend fun run(params: String): Either<Failure, SubscriptionListResponse> {
        return apiRepository.getSubscriptions("Bearer $params")
    }
}