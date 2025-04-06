package modules.lib.lyrics.domain.usecase

import modules.lib.lyrics.domain.repository.LrcRepository
import modules.lib.lyrics.domain.models.Track
import javax.inject.Inject

class SearchLyricsUseCase @Inject constructor(
    private val repository: LrcRepository
) {
    suspend operator fun invoke(
        artist: String, title: String
    ): Result<List<Track>> = repository.searchLyrics(artist, title)
}