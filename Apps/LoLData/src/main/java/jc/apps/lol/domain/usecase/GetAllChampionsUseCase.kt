package jc.apps.lol.domain.usecase

import jc.apps.lol.data.model.ChampionResponseModel
import jc.apps.lol.domain.repository.ApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import modules.common.usecase.UseCaseWithoutParams
import javax.inject.Inject

class GetAllChampionsUseCase @Inject constructor(
    private val apiRepository: ApiRepository
) : UseCaseWithoutParams<ChampionResponseModel>() {
    override suspend fun run(): Either<Failure, ChampionResponseModel> {
        return apiRepository.getAllChampions()
    }
}