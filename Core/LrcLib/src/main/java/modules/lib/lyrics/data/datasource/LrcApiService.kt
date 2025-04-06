package modules.lib.lyrics.data.datasource

import modules.lib.lyrics.domain.models.Track
import retrofit2.http.GET
import retrofit2.http.Query

interface LrcApiService {
    @GET("/api/search")
    suspend fun searchLyrics(
        @Query("track_name") trackName: String,
        @Query("artist_name") artistName: String,
        @Query("album_name") albumName: String? = null
    ): List<Track>
}