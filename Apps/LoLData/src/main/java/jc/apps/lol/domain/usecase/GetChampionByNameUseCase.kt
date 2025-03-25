package jc.apps.lol.domain.usecase

import jc.apps.lol.data.model.ChampionResponseModel
import jc.apps.lol.domain.repository.ApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithParams
import javax.inject.Inject

class GetChampionByNameUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) : UseCaseWithParams<ChampionResponseModel, String>() {
    override suspend fun run(params: String): Either<Failure, ChampionResponseModel> {
        return apiRepository.getChampionByName(params)
    }
}