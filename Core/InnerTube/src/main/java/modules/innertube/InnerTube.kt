package modules.innertube

import modules.innertube.models.Context
import modules.innertube.models.YouTubeClient
import modules.innertube.models.YouTubeLocale
import modules.innertube.models.body.AccountMenuBody
import modules.innertube.models.body.BrowseBody
import modules.innertube.models.body.GetQueueBody
import modules.innertube.models.body.GetSearchSuggestionsBody
import modules.innertube.models.body.GetTranscriptBody
import modules.innertube.models.body.NextBody
import modules.innertube.models.body.PlayerBody
import modules.innertube.models.body.SearchBody
import modules.innertube.utils.parseCookieString
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.Locale

class InnerTube {
    private var httpClient: OkHttpClient = createOkHttpClient()
    private var retrofit: Retrofit = createRetrofit()

    var visitorData: String = "CgtsZG1ySnZiQWtSbyiMjuGSBg%3D%3D"
    var cookie: String? = null
        set(value) {
            field = value
            cookieMap = if (value == null) emptyMap() else parseCookieString(value)
        }
    private var cookieMap = emptyMap<String, String>()

    var proxy: Proxy? = null
        set(value) {
            field = value
            httpClient = createOkHttpClient()
            retrofit = createRetrofit()
        }

    var useLoginForBrowse: Boolean = false

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (proxy != null) {
                proxy(proxy)
            }
            // Add interceptors for gzip, brotli, etc., if needed
        }.build()
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://music.youtube.com/youtubei/v1/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build()
    }

    private fun getService(): InnerTubeService = retrofit.create(InnerTubeService::class.java)

    private fun buildHeaders(
        client: YouTubeClient, setLogin: Boolean = false
    ): Map<String, String> {
        val headers = mutableMapOf<String, String>(
            "X-Goog-Api-Format-Version" to "1",
            "X-YouTube-Client-Name" to client.clientName,
            "X-YouTube-Client-Version" to client.clientVersion,
            "x-origin" to "https://music.youtube.com",
            "User-Agent" to client.userAgent,
            "Content-Type" to "application/json"
        )
        client.referer?.let { headers["Referer"] = it }
        if (setLogin) {
            cookie?.let { cookie ->
                headers["cookie"] = cookie
                if ("SAPISID" in cookieMap) {
                    val currentTime = System.currentTimeMillis() / 1000
                    val sapisidHash =
                        sha1("$currentTime ${cookieMap["SAPISID"]} https://music.youtube.com")
                    headers["Authorization"] = "SAPISIDHASH ${currentTime}_${sapisidHash}"
                }
            }
        }
        return headers
    }

    var locale = YouTubeLocale(
        gl = Locale.getDefault().country, hl = Locale.getDefault().toLanguageTag()
    )

    suspend fun search(
        client: YouTubeClient,
        query: String? = null,
        params: String? = null,
        continuation: String? = null
    ) = getService().search(
        headers = buildHeaders(client, useLoginForBrowse),
        continuation = continuation,
        ctoken = continuation,
        body = SearchBody(
            context = client.toContext(locale, visitorData), query = query, params = params
        )
    )

    suspend fun player(
        client: YouTubeClient, videoId: String, playlistId: String?
    ) = getService().player(
        headers = buildHeaders(client, setLogin = true), body = PlayerBody(
            context = client.toContext(locale, visitorData).let {
                if (client == YouTubeClient.TVHTML5) {
                    it.copy(thirdParty = Context.ThirdParty(embedUrl = "https://www.youtube.com/watch?v=${videoId}"))
                } else it
            }, videoId = videoId, playlistId = playlistId
        )
    )

    suspend fun pipedStreams(videoId: String) = getService().pipedStreams(videoId)

    suspend fun browse(
        client: YouTubeClient,
        browseId: String? = null,
        params: String? = null,
        continuation: String? = null,
        setLogin: Boolean = false
    ) = getService().browse(
        headers = buildHeaders(client, setLogin || useLoginForBrowse),
        continuation = continuation,
        ctoken = continuation,
        type = if (continuation != null) "next" else null,
        body = BrowseBody(
            context = client.toContext(locale, visitorData), browseId = browseId, params = params
        )
    )

    suspend fun next(
        client: YouTubeClient,
        videoId: String?,
        playlistId: String?,
        playlistSetVideoId: String?,
        index: Int?,
        params: String?,
        continuation: String? = null
    ) = getService().next(
        headers = buildHeaders(client, setLogin = true), body = NextBody(
            context = client.toContext(locale, visitorData),
            videoId = videoId,
            playlistId = playlistId,
            playlistSetVideoId = playlistSetVideoId,
            index = index,
            params = params,
            continuation = continuation
        )
    )

    suspend fun getSearchSuggestions(
        client: YouTubeClient, input: String
    ) = getService().getSearchSuggestions(
        headers = buildHeaders(client), body = GetSearchSuggestionsBody(
            context = client.toContext(locale, visitorData), input = input
        )
    )

    suspend fun getQueue(
        client: YouTubeClient, videoIds: List<String>?, playlistId: String?
    ) = getService().getQueue(
        headers = buildHeaders(client), body = GetQueueBody(
            context = client.toContext(locale, visitorData),
            videoIds = videoIds,
            playlistId = playlistId
        )
    )

    suspend fun getTranscript(
        client: YouTubeClient, videoId: String
    ) = getService().getTranscript(
        body = GetTranscriptBody(
            context = client.toContext(locale, null),
            params = "\n${11.toChar()}$videoId".encodeBase64()
        )
    )

    suspend fun getSwJsData() = getService().getSwJsData()

    suspend fun accountMenu(client: YouTubeClient) = getService().accountMenu(
        headers = buildHeaders(client, setLogin = true),
        body = AccountMenuBody(client.toContext(locale, visitorData))
    )
}

// Placeholder for sha1 and encodeBase64 (implement these as needed)
fun sha1(input: String): String {
    // Implement SHA-1 hashing
    return input // Replace with actual implementation
}

fun String.encodeBase64(): String {
    // Implement Base64 encoding
    return this // Replace with actual implementation
}