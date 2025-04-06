package modules.lib.lyrics.data.datasource

import javax.inject.Inject

class LrcRemoteDataSource @Inject constructor(
    private val apiService: LrcApiService
) {
    suspend fun searchLyrics(
        artist: String, title: String, album: String?
    ) = apiService.searchLyrics(title, artist, album)
}