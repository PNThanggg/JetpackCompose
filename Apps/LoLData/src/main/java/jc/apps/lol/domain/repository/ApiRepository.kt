package jc.apps.lol.domain.repository

import jc.apps.lol.data.model.ChampionResponseModel
import modules.common.failure.Failure
import modules.common.functional.Either

interface ApiRepository {
    suspend fun getAllChampions(): Either<Failure, ChampionResponseModel>

    suspend fun getChampionByName(name: String): Either<Failure, ChampionResponseModel>
}