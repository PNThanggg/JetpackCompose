package jc.apps.lol.domain.usecase

import jc.apps.lol.data.model.ChampionResponseModel
import jc.apps.lol.domain.repository.ApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either

class GetAllChampionsUseCase(
    private val apiRepository: ApiRepository
) {
    suspend operator fun invoke(): Either<Failure, ChampionResponseModel> {
        return apiRepository.getAllChampions()
    }
}