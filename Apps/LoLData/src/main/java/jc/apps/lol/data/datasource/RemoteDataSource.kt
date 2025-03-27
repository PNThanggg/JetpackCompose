package jc.apps.lol.data.datasource

import jc.apps.lol.data.datasource.remote.ApiService
import jc.apps.lol.data.model.ChampionResponseModel
import modules.common.failure.Failure
import modules.common.failure.Failure.ServerError
import modules.common.functional.Either
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getAllChampions(): Either<Failure, ChampionResponseModel> {
        return try {
            val response = apiService.getAllChampions()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Either.Right(body)
                } else {
                    Either.Left(ServerError("No champion data found"))
                }
            } else {
                Either.Left(ServerError(response.message()))
            }
        } catch (e: Exception) {
            Either.Left(ServerError(e.message ?: "Unknown error"))
        }
    }

    suspend fun getChampionByName(name: String): Either<Failure, ChampionResponseModel> {
        val response = apiService.getChampionByName(name)

        return if (response.isSuccessful) {
            return if (response.body() != null) {
                Either.Right(response.body()!!)
            } else {
                Either.Left(ServerError("No champion found"))
            }
        } else {
            Either.Left(ServerError(response.message()))
        }
    }
}