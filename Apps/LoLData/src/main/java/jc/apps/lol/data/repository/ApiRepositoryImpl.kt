package jc.apps.lol.data.repository

import jc.apps.lol.data.datasource.RemoteDataSource
import jc.apps.lol.data.model.ChampionResponseModel
import jc.apps.lol.domain.repository.ApiRepository
import modules.common.failure.Failure
import modules.common.functional.Either
import javax.inject.Inject

class ApiRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ApiRepository {
    override suspend fun getAllChampions(): Either<Failure, ChampionResponseModel> {
        return remoteDataSource.getAllChampions()
    }

    override suspend fun getChampionByName(name: String): Either<Failure, ChampionResponseModel> {
        return remoteDataSource.getChampionByName(name)
    }
}