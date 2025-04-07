package jc.apps.music.lyrics

import android.content.Context
import modules.lib.lyrics.domain.usecase.GetAllLyricsUseCase
import modules.lib.lyrics.domain.usecase.GetLyricsUseCase
import javax.inject.Inject


class LrcLibLyricsProvider @Inject constructor(
    private val getLyricsUseCase: GetLyricsUseCase,
    private val getAllLyricsUseCase: GetAllLyricsUseCase,
) : LyricsProvider {
    override val name = "LrcLib"

    override fun isEnabled(context: Context): Boolean = true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
    ): Result<String> = getLyricsUseCase.invoke(title, artist, duration)

    override suspend fun getAllLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
    ): Result<List<String>> = getAllLyricsUseCase.invoke(title, artist, duration, null)
}
