package modules.lib.lyrics.domain.usecase

import modules.lib.lyrics.domain.repository.LrcRepository
import javax.inject.Inject

class GetAllLyricsUseCase @Inject constructor(
    private val repository: LrcRepository
) {
    suspend operator fun invoke(
        title: String,
        artist: String,
        duration: Int,
        album: String? = null,
    ): Result<List<String>> = repository.getAllLyrics(title, artist, duration, album)
}