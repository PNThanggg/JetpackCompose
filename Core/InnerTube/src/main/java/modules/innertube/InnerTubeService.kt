package modules.innertube

import modules.innertube.models.body.AccountMenuBody
import modules.innertube.models.body.BrowseBody
import modules.innertube.models.body.GetQueueBody
import modules.innertube.models.body.GetSearchSuggestionsBody
import modules.innertube.models.body.GetTranscriptBody
import modules.innertube.models.body.NextBody
import modules.innertube.models.body.PlayerBody
import modules.innertube.models.body.SearchBody
import modules.innertube.models.response.AccountMenuResponse
import modules.innertube.models.response.BrowseResponse
import modules.innertube.models.response.GetQueueResponse
import modules.innertube.models.response.GetSearchSuggestionsResponse
import modules.innertube.models.response.GetTranscriptResponse
import modules.innertube.models.response.NextResponse
import modules.innertube.models.response.PipedResponse
import modules.innertube.models.response.PlayerResponse
import modules.innertube.models.response.SearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface InnerTubeService {
    @POST("search")
    suspend fun search(
        @HeaderMap headers: Map<String, String>,
        @Query("continuation") continuation: String?,
        @Query("ctoken") ctoken: String?,
        @Body body: SearchBody
    ): Response<SearchResponse>

    @POST("player")
    suspend fun player(
        @HeaderMap headers: Map<String, String>, @Body body: PlayerBody
    ): Response<PlayerResponse>

    @GET("https://pipedapi.kavin.rocks/streams/{videoId}")
    suspend fun pipedStreams(
        @Path("videoId") videoId: String
    ): Response<PipedResponse>

    @POST("browse")
    suspend fun browse(
        @HeaderMap headers: Map<String, String>,
        @Query("continuation") continuation: String?,
        @Query("ctoken") ctoken: String?,
        @Query("type") type: String?,
        @Body body: BrowseBody
    ): Response<BrowseResponse>

    @POST("next")
    suspend fun next(
        @HeaderMap headers: Map<String, String>, @Body body: NextBody
    ): Response<NextResponse>

    @POST("music/get_search_suggestions")
    suspend fun getSearchSuggestions(
        @HeaderMap headers: Map<String, String>, @Body body: GetSearchSuggestionsBody
    ): Response<GetSearchSuggestionsResponse>

    @POST("music/get_queue")
    suspend fun getQueue(
        @HeaderMap headers: Map<String, String>, @Body body: GetQueueBody
    ): Response<GetQueueResponse>

    @POST("https://music.youtube.com/youtubei/v1/get_transcript")
    suspend fun getTranscript(
        @Header("Content-Type") contentType: String = "application/json",
        @Query("key") key: String = "AIzaSyC9XL3ZjWddXya6X74dJoCTL-WEYFDNX3",
        @Body body: GetTranscriptBody
    ): Response<GetTranscriptResponse>

    @GET("https://music.youtube.com/sw.js_data")
    suspend fun getSwJsData(): Response<String>

    @POST("account/account_menu")
    suspend fun accountMenu(
        @HeaderMap headers: Map<String, String>, @Body body: AccountMenuBody
    ): Response<AccountMenuResponse>
}