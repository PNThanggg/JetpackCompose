package modules.lib.lyrics.domain.usecase

import modules.lib.lyrics.domain.repository.LrcRepository
import javax.inject.Inject

class GetLyricsUseCase @Inject constructor(
    private val repository: LrcRepository
) {
    suspend operator fun invoke(
        artist: String, title: String, duration: Int, album: String? = null
    ): Result<String> = repository.getLyrics(artist, title, duration, album)
}