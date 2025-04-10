package jc.apps.music.lyrics

import android.content.Context
import modules.innertube.YouTube

object YouTubeSubtitleLyricsProvider : LyricsProvider {
    override val name = "YouTube Subtitle"

    override fun isEnabled(context: Context) = true

    override suspend fun getLyrics(
        id: String, title: String, artist: String, duration: Int
    ): Result<String> = YouTube.transcript(id)

    override suspend fun getAllLyrics(
        id: String, title: String, artist: String, duration: Int
    ): Result<List<String>> = runCatching {
        emptyList<String>()
    }
}
