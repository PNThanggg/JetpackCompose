package jc.apps.lol.data.datasource.remote

import jc.apps.lol.data.model.ChampionResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("Content-Type: application/json")
    @GET("champion.json")
    suspend fun getAllChampions(): Response<ChampionResponseModel>

    @GET("champion/{name}.json")
    suspend fun getChampionByName(name: String): Response<ChampionResponseModel>
}