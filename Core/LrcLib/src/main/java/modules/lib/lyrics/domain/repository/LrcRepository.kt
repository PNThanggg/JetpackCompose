package modules.lib.lyrics.domain.repository

import modules.lib.lyrics.domain.models.Track

interface LrcRepository {
    suspend fun getLyrics(
        artist: String, title: String, duration: Int, album: String?
    ): Result<String>

    suspend fun getAllLyrics(
        title: String, artist: String, duration: Int, album: String?
    ): Result<List<String>>

    suspend fun searchLyrics(artist: String, title: String): Result<List<Track>>
}