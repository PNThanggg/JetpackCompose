package modules.lib.lyrics.data.repository

import modules.lib.lyrics.data.datasource.LrcRemoteDataSource
import modules.lib.lyrics.domain.models.Lyrics
import modules.lib.lyrics.domain.models.Track
import modules.lib.lyrics.domain.models.bestMatchingFor
import modules.lib.lyrics.domain.repository.LrcRepository
import javax.inject.Inject
import kotlin.math.abs

class LrcRepositoryImpl @Inject constructor(
    private val remoteDataSource: LrcRemoteDataSource
) : LrcRepository {

    override suspend fun getLyrics(
        artist: String, title: String, duration: Int, album: String?
    ): Result<String> = runCatching {
        val tracks =
            remoteDataSource.searchLyrics(artist, title, album).filter { it.syncedLyrics != null }
        val bestMatch = tracks.bestMatchingFor(duration)
        bestMatch?.syncedLyrics?.let { Lyrics(it).text }
            ?: throw IllegalStateException("Lyrics unavailable")
    }

    override suspend fun getAllLyrics(
        title: String, artist: String, duration: Int, album: String?,
    ): Result<List<String>> = runCatching {
        val tracks = remoteDataSource.searchLyrics(artist, title, album)
            .filter { it.syncedLyrics != null || it.plainLyrics != null }

        val lyricsList = mutableListOf<String>()
        var count = 0
        var plain = 0

        for (track in tracks) {
            if (count <= 4) {
                when {
                    track.syncedLyrics != null && duration == -1 -> {
                        count++
                        lyricsList.add(track.syncedLyrics)
                    }

                    track.syncedLyrics != null && abs(track.duration - duration) <= 2 -> {
                        count++
                        lyricsList.add(track.syncedLyrics)
                    }

                    track.plainLyrics != null && abs(track.duration - duration) <= 2 && plain == 0 -> {
                        count++
                        plain++
                        lyricsList.add(track.plainLyrics)
                    }
                }
            } else {
                break
            }
        }

        lyricsList
    }

    override suspend fun searchLyrics(artist: String, title: String): Result<List<Track>> =
        runCatching {
            remoteDataSource.searchLyrics(artist, title, null).filter { it.syncedLyrics != null }
                .map { it }
        }
}