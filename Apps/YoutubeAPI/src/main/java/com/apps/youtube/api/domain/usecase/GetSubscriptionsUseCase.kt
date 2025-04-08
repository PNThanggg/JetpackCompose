package com.apps.youtube.api.domain.usecase

import com.apps.youtube.api.data.models.SubscriptionListResponse
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithoutParams
import javax.inject.Inject

class GetSubscriptionsUseCase @Inject constructor(
    private val apiRepository: YoutubeApiRepository
) : UseCaseWithoutParams<SubscriptionListResponse>() {
    override suspend fun run(): Either<Failure, SubscriptionListResponse> {
        return apiRepository.getSubscriptions()
    }
}