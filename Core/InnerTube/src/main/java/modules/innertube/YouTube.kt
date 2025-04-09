package modules.innertube

import NextPage
import NextResult
import com.google.gson.JsonParser
import modules.innertube.models.AccountInfo
import modules.innertube.models.AlbumItem
import modules.innertube.models.Artist
import modules.innertube.models.ArtistItem
import modules.innertube.models.BrowseEndpoint
import modules.innertube.models.GridRenderer
import modules.innertube.models.MusicCarouselShelfRenderer
import modules.innertube.models.PlaylistItem
import modules.innertube.models.SearchSuggestions
import modules.innertube.models.SongItem
import modules.innertube.models.WatchEndpoint
import modules.innertube.models.WatchEndpoint.WatchEndpointMusicSupportedConfigs.WatchEndpointMusicConfig.Companion.MUSIC_VIDEO_TYPE_ATV
import modules.innertube.models.YouTubeClient.Companion.ANDROID_MUSIC
import modules.innertube.models.YouTubeClient.Companion.IOS
import modules.innertube.models.YouTubeClient.Companion.TVHTML5
import modules.innertube.models.YouTubeClient.Companion.WEB
import modules.innertube.models.YouTubeClient.Companion.WEB_REMIX
import modules.innertube.models.YouTubeLocale
import modules.innertube.models.getContinuation
import modules.innertube.models.oddElements
import modules.innertube.models.response.PlayerResponse
import modules.innertube.pages.AlbumPage
import modules.innertube.pages.ArtistItemsContinuationPage
import modules.innertube.pages.ArtistItemsPage
import modules.innertube.pages.ArtistPage
import modules.innertube.pages.BrowseResult
import modules.innertube.pages.ExplorePage
import modules.innertube.pages.HomePage
import modules.innertube.pages.MoodAndGenres
import modules.innertube.pages.NewReleaseAlbumPage
import modules.innertube.pages.PlaylistContinuationPage
import modules.innertube.pages.PlaylistPage
import modules.innertube.pages.RelatedPage
import modules.innertube.pages.SearchPage
import modules.innertube.pages.SearchResult
import modules.innertube.pages.SearchSuggestionPage
import modules.innertube.pages.SearchSummary
import modules.innertube.pages.SearchSummaryPage
import java.net.Proxy

/**
 * Parse useful data with [InnerTube] sending requests.
 * Modified from [ViMusic](https://github.com/vfsfitvnm/ViMusic)
 */
object YouTube {
    private val innerTube = InnerTube()

    var locale: YouTubeLocale
        get() = innerTube.locale
        set(value) {
            innerTube.locale = value
        }
    var visitorData: String
        get() = innerTube.visitorData
        set(value) {
            innerTube.visitorData = value
        }
    var cookie: String?
        get() = innerTube.cookie
        set(value) {
            innerTube.cookie = value
        }
    var proxy: Proxy?
        get() = innerTube.proxy
        set(value) {
            innerTube.proxy = value
        }
    var useLoginForBrowse: Boolean
        get() = innerTube.useLoginForBrowse
        set(value) {
            innerTube.useLoginForBrowse = value
        }

    suspend fun searchSuggestions(query: String): Result<SearchSuggestions> = runCatching {
        val response = innerTube.getSearchSuggestions(WEB_REMIX, query)
        val queries =
            response.body()?.contents?.getOrNull(0)?.searchSuggestionsSectionRenderer?.contents?.mapNotNull { content ->
                content.searchSuggestionRenderer?.suggestion?.runs?.joinToString(separator = "") { it.text }
            }.orEmpty()
        val recommendedItems =
            response.body()?.contents?.getOrNull(1)?.searchSuggestionsSectionRenderer?.contents?.mapNotNull {
                it.musicResponsiveListItemRenderer?.let { renderer ->
                    SearchSuggestionPage.fromMusicResponsiveListItemRenderer(renderer)
                }
            }.orEmpty()

        SearchSuggestions(
            queries = queries,
            recommendedItems = recommendedItems,
        )
    }

    suspend fun searchSummary(query: String): Result<SearchSummaryPage> = runCatching {
        val response = innerTube.search(WEB_REMIX, query)
        SearchSummaryPage(
            summaries = response.body()?.contents?.tabbedSearchResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.mapNotNull { it ->
                if (it.musicCardShelfRenderer != null) SearchSummary(
                    title = it.musicCardShelfRenderer.header.musicCardShelfHeaderBasicRenderer.title.runs?.firstOrNull()?.text
                    ?: return@mapNotNull null,
                    items = listOfNotNull(SearchSummaryPage.fromMusicCardShelfRenderer(it.musicCardShelfRenderer)).plus(
                        it.musicCardShelfRenderer.contents?.mapNotNull { it.musicResponsiveListItemRenderer }
                            ?.mapNotNull(SearchSummaryPage.Companion::fromMusicResponsiveListItemRenderer)
                            .orEmpty()).distinctBy { it.id }.ifEmpty { null }
                        ?: return@mapNotNull null)
                else SearchSummary(
                    title = it.musicShelfRenderer?.title?.runs?.firstOrNull()?.text
                    ?: return@mapNotNull null,
                    items = it.musicShelfRenderer.contents?.mapNotNull {
                        SearchSummaryPage.fromMusicResponsiveListItemRenderer(it.musicResponsiveListItemRenderer)
                    }?.distinctBy { it.id }?.ifEmpty { null } ?: return@mapNotNull null)
            }.orEmpty()
        )
    }

    suspend fun search(query: String, filter: SearchFilter): Result<SearchResult> = runCatching {
        val response = innerTube.search(WEB_REMIX, query, filter.value)
        val items =
            response.body()?.contents?.tabbedSearchResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.lastOrNull()?.musicShelfRenderer?.contents?.mapNotNull {
                SearchPage.toYTItem(it.musicResponsiveListItemRenderer)
            }.orEmpty()
        val continuation =
            response.body()?.contents?.tabbedSearchResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.lastOrNull()?.musicShelfRenderer?.continuations?.getContinuation()

        SearchResult(
            items = items,
            continuation = continuation,
        )
    }

    suspend fun searchContinuation(continuation: String): Result<SearchResult> = runCatching {
        val response = innerTube.search(WEB_REMIX, continuation = continuation)
        SearchResult(
            items = response.body()?.continuationContents?.musicShelfContinuation?.contents?.mapNotNull {
                SearchPage.toYTItem(it.musicResponsiveListItemRenderer)
            }!!,
            continuation = response.body()?.continuationContents?.musicShelfContinuation?.continuations?.getContinuation()
        )
    }

    suspend fun album(browseId: String, withSongs: Boolean = true): Result<AlbumPage> =
        runCatching {
            val response = innerTube.browse(WEB_REMIX, browseId)
            val playlistId =
                response.body()?.microformat?.microformatDataRenderer?.urlCanonical?.substringAfterLast(
                    '='
                ) ?: ""
            AlbumPage(
                album = AlbumItem(
                browseId = browseId,
                playlistId = playlistId,
                title = response.body()?.contents?.twoColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.musicResponsiveHeaderRenderer?.title?.runs?.firstOrNull()?.text!!,
                artists = response.body()?.contents?.twoColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.musicResponsiveHeaderRenderer?.straplineTextOne?.runs?.oddElements()
                    ?.map {
                        Artist(
                            name = it.text, id = it.navigationEndpoint?.browseEndpoint?.browseId
                        )
                    }!!,
                year = response.body()?.contents?.twoColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.musicResponsiveHeaderRenderer?.subtitle?.runs?.lastOrNull()?.text?.toIntOrNull(),
                thumbnail = response.body()?.contents?.twoColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.musicResponsiveHeaderRenderer?.thumbnail?.musicThumbnailRenderer?.thumbnail?.thumbnails?.lastOrNull()?.url!!,
            ),
                songs = if (withSongs) albumSongs(playlistId).getOrThrow() else emptyList(),
                otherVersions = response.body()?.contents?.twoColumnBrowseResultsRenderer?.secondaryContents?.sectionListRenderer?.contents?.getOrNull(
                    1
                )?.musicCarouselShelfRenderer?.contents?.mapNotNull { it.musicTwoRowItemRenderer }
                    ?.mapNotNull(NewReleaseAlbumPage::fromMusicTwoRowItemRenderer).orEmpty())
        }

    suspend fun albumSongs(playlistId: String): Result<List<SongItem>> = runCatching {
        var response = innerTube.browse(WEB_REMIX, "VL$playlistId")
        val songs =
            response.body()?.contents?.twoColumnBrowseResultsRenderer?.secondaryContents?.sectionListRenderer?.contents?.firstOrNull()?.musicPlaylistShelfRenderer?.contents?.mapNotNull {
                AlbumPage.fromMusicResponsiveListItemRenderer(it.musicResponsiveListItemRenderer)
            }?.toMutableList()
        var continuation =
            response.body()?.contents?.twoColumnBrowseResultsRenderer?.secondaryContents?.sectionListRenderer?.contents?.firstOrNull()?.musicPlaylistShelfRenderer?.continuations?.getContinuation()
        while (continuation != null) {
            response = innerTube.browse(
                client = WEB_REMIX,
                continuation = continuation,
            )

            val item =
                response.body()?.continuationContents?.musicPlaylistShelfContinuation?.contents?.mapNotNull {
                    AlbumPage.fromMusicResponsiveListItemRenderer(it.musicResponsiveListItemRenderer)
                }.orEmpty()

            songs?.plusAssign(item)

            continuation =
                response.body()?.continuationContents?.musicPlaylistShelfContinuation?.continuations?.getContinuation()
        }
        songs.orEmpty()
    }

    suspend fun artist(browseId: String): Result<ArtistPage> = runCatching {
        val response = innerTube.browse(WEB_REMIX, browseId)
        ArtistPage(
            artist = ArtistItem(
                id = browseId,
                title = response.body()?.header?.musicImmersiveHeaderRenderer?.title?.runs?.firstOrNull()?.text
                    ?: response.body()?.header?.musicVisualHeaderRenderer?.title?.runs?.firstOrNull()?.text!!,
                thumbnail = response.body()?.header?.musicImmersiveHeaderRenderer?.thumbnail?.musicThumbnailRenderer?.getThumbnailUrl()
                    ?: response.body()?.header?.musicVisualHeaderRenderer?.foregroundThumbnail?.musicThumbnailRenderer?.getThumbnailUrl()!!,
                shuffleEndpoint = response.body()?.header?.musicImmersiveHeaderRenderer?.playButton?.buttonRenderer?.navigationEndpoint?.watchEndpoint,
                radioEndpoint = response.body()?.header?.musicImmersiveHeaderRenderer?.startRadioButton?.buttonRenderer?.navigationEndpoint?.watchEndpoint
            ),
            sections = response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.mapNotNull(
                ArtistPage::fromSectionListRendererContent
            ).orEmpty(),
            description = response.body()?.header?.musicImmersiveHeaderRenderer?.description?.runs?.firstOrNull()?.text
        )
    }

    suspend fun artistItems(endpoint: BrowseEndpoint): Result<ArtistItemsPage> = runCatching {
        val response = innerTube.browse(WEB_REMIX, endpoint.browseId, endpoint.params)
        val gridRenderer =
            response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.gridRenderer
        if (gridRenderer != null) {
            ArtistItemsPage(
                title = gridRenderer.header?.gridHeaderRenderer?.title?.runs?.firstOrNull()?.text.orEmpty(),
                items = gridRenderer.items.mapNotNull {
                    it.musicTwoRowItemRenderer?.let { renderer ->
                        ArtistItemsPage.fromMusicTwoRowItemRenderer(renderer)
                    }
                },
                continuation = gridRenderer.continuations?.getContinuation()
            )
        } else {
            ArtistItemsPage(
                title = response.body()?.header?.musicHeaderRenderer?.title?.runs?.firstOrNull()?.text!!,
                items = response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.musicPlaylistShelfRenderer?.contents?.mapNotNull {
                    ArtistItemsPage.fromMusicResponsiveListItemRenderer(it.musicResponsiveListItemRenderer)
                }!!,
                continuation = response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.musicPlaylistShelfRenderer?.continuations?.getContinuation()
            )
        }
    }

    suspend fun artistItemsContinuation(continuation: String): Result<ArtistItemsContinuationPage> =
        runCatching {
            val response = innerTube.browse(WEB_REMIX, continuation = continuation)
            val gridContinuation = response.body()?.continuationContents?.gridContinuation
            if (gridContinuation != null) {
                ArtistItemsContinuationPage(
                    items = gridContinuation.items.mapNotNull {
                        it.musicTwoRowItemRenderer?.let { renderer ->
                            ArtistItemsPage.fromMusicTwoRowItemRenderer(renderer)
                        }
                    }, continuation = gridContinuation.continuations?.getContinuation()
                )
            } else {
                ArtistItemsContinuationPage(
                    items = response.body()?.continuationContents?.musicPlaylistShelfContinuation?.contents?.mapNotNull {
                        ArtistItemsPage.fromMusicResponsiveListItemRenderer(it.musicResponsiveListItemRenderer)
                    }!!,
                    continuation = response.body()?.continuationContents?.musicPlaylistShelfContinuation?.continuations?.getContinuation()
                )
            }
        }

    suspend fun playlist(playlistId: String): Result<PlaylistPage> = runCatching {
        val response = innerTube.browse(
            client = WEB_REMIX, browseId = "VL$playlistId", setLogin = true
        )
        val base =
            response.body()?.contents?.twoColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()
        val header = base?.musicResponsiveHeaderRenderer
            ?: base?.musicEditablePlaylistDetailHeaderRenderer?.header?.musicResponsiveHeaderRenderer
        PlaylistPage(
            playlist = PlaylistItem(
                id = playlistId,
                title = header?.title?.runs?.firstOrNull()?.text!!,
                author = header.straplineTextOne?.runs?.firstOrNull()?.let {
                    Artist(
                        name = it.text, id = it.navigationEndpoint?.browseEndpoint?.browseId
                    )
                },
                songCountText = header.secondSubtitle?.runs?.firstOrNull()?.text,
                thumbnail = header.thumbnail?.musicThumbnailRenderer?.thumbnail?.thumbnails?.lastOrNull()?.url!!,
                playEndpoint = null,
                shuffleEndpoint = header.buttons?.lastOrNull()?.menuRenderer?.items?.firstOrNull()?.menuNavigationItemRenderer?.navigationEndpoint?.watchPlaylistEndpoint!!,
                radioEndpoint = header.buttons.lastOrNull()?.menuRenderer?.items!!.find {
                    it.menuNavigationItemRenderer?.icon?.iconType == "MIX"
                }?.menuNavigationItemRenderer?.navigationEndpoint?.watchPlaylistEndpoint!!
            ),
            songs = response.body()?.contents?.twoColumnBrowseResultsRenderer?.secondaryContents?.sectionListRenderer?.contents?.firstOrNull()?.musicPlaylistShelfRenderer?.contents?.mapNotNull {
                PlaylistPage.fromMusicResponsiveListItemRenderer(it.musicResponsiveListItemRenderer)
            }!!,
            songsContinuation = response.body()?.contents?.twoColumnBrowseResultsRenderer?.secondaryContents?.sectionListRenderer?.contents?.firstOrNull()?.musicPlaylistShelfRenderer?.continuations?.getContinuation(),
            continuation = response.body()?.contents?.twoColumnBrowseResultsRenderer?.secondaryContents?.sectionListRenderer?.continuations?.getContinuation()
        )
    }

    suspend fun playlistContinuation(continuation: String) = runCatching {
        val response = innerTube.browse(
            client = WEB_REMIX, continuation = continuation, setLogin = true
        )
        PlaylistContinuationPage(
            songs = response.body()?.continuationContents?.musicPlaylistShelfContinuation?.contents?.mapNotNull {
                PlaylistPage.fromMusicResponsiveListItemRenderer(it.musicResponsiveListItemRenderer)
            }!!,
            continuation = response.body()?.continuationContents?.musicPlaylistShelfContinuation?.continuations?.getContinuation()
        )
    }

    suspend fun home(): Result<HomePage> = runCatching {
        var response = innerTube.browse(WEB_REMIX, browseId = "FEmusic_home")
        var continuation =
            response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.continuations?.getContinuation()
        val sections =
            response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents!!.mapNotNull { it.musicCarouselShelfRenderer }
                .mapNotNull {
                    HomePage.Section.fromMusicCarouselShelfRenderer(it)
                }.toMutableList()
        while (continuation != null) {
            response = innerTube.browse(WEB_REMIX, continuation = continuation)
            continuation =
                response.body()?.continuationContents?.sectionListContinuation?.continuations?.getContinuation()
            sections += response.body()?.continuationContents?.sectionListContinuation?.contents?.mapNotNull { it.musicCarouselShelfRenderer }
                ?.mapNotNull {
                    HomePage.Section.fromMusicCarouselShelfRenderer(it)
                }.orEmpty()
        }
        HomePage(sections)
    }

    suspend fun explore(): Result<ExplorePage> = runCatching {
        val response = innerTube.browse(WEB_REMIX, browseId = "FEmusic_explore")
        ExplorePage(newReleaseAlbums = response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.find {
            it.musicCarouselShelfRenderer?.header?.musicCarouselShelfBasicHeaderRenderer?.moreContentButton?.buttonRenderer?.navigationEndpoint?.browseEndpoint?.browseId == "FEmusic_new_releases_albums"
        }?.musicCarouselShelfRenderer?.contents?.mapNotNull { it.musicTwoRowItemRenderer }
            ?.mapNotNull(NewReleaseAlbumPage::fromMusicTwoRowItemRenderer).orEmpty(),
            moodAndGenres = response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.find {
                it.musicCarouselShelfRenderer?.header?.musicCarouselShelfBasicHeaderRenderer?.moreContentButton?.buttonRenderer?.navigationEndpoint?.browseEndpoint?.browseId == "FEmusic_moods_and_genres"
            }?.musicCarouselShelfRenderer?.contents?.mapNotNull { it.musicNavigationButtonRenderer }
                ?.mapNotNull(MoodAndGenres.Companion::fromMusicNavigationButtonRenderer).orEmpty())
    }

    suspend fun newReleaseAlbums(): Result<List<AlbumItem>> = runCatching {
        val response = innerTube.browse(WEB_REMIX, browseId = "FEmusic_new_releases_albums")
        response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.gridRenderer?.items?.mapNotNull { it.musicTwoRowItemRenderer }
            ?.mapNotNull(NewReleaseAlbumPage::fromMusicTwoRowItemRenderer).orEmpty()
    }

    suspend fun moodAndGenres(): Result<List<MoodAndGenres>> = runCatching {
        val response = innerTube.browse(WEB_REMIX, browseId = "FEmusic_moods_and_genres")
        response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents!!.mapNotNull(
            MoodAndGenres.Companion::fromSectionListRendererContent
        )
    }

    suspend fun browse(browseId: String, params: String?): Result<BrowseResult> = runCatching {
        val response = innerTube.browse(WEB_REMIX, browseId = browseId, params = params)
        BrowseResult(
            title = response.body()?.header?.musicHeaderRenderer?.title?.runs?.firstOrNull()?.text,
            items = response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.mapNotNull { content ->
                when {
                    content.gridRenderer != null -> {
                        BrowseResult.Item(
                            title = content.gridRenderer.header?.gridHeaderRenderer?.title?.runs?.firstOrNull()?.text,
                            items = content.gridRenderer.items.mapNotNull(GridRenderer.Item::musicTwoRowItemRenderer)
                                .mapNotNull(RelatedPage.Companion::fromMusicTwoRowItemRenderer)
                        )
                    }

                    content.musicCarouselShelfRenderer != null -> {
                        BrowseResult.Item(
                            title = content.musicCarouselShelfRenderer.header?.musicCarouselShelfBasicHeaderRenderer?.title?.runs?.firstOrNull()?.text,
                            items = content.musicCarouselShelfRenderer.contents.mapNotNull(
                                MusicCarouselShelfRenderer.Content::musicTwoRowItemRenderer
                            ).mapNotNull(RelatedPage.Companion::fromMusicTwoRowItemRenderer)
                        )
                    }

                    else -> null
                }
            }.orEmpty()
        )
    }

    suspend fun likedPlaylists(): Result<List<PlaylistItem>> = runCatching {
        val response = innerTube.browse(
            client = WEB_REMIX, browseId = "FEmusic_liked_playlists", setLogin = true
        )
        response.body()?.contents?.singleColumnBrowseResultsRenderer?.tabs?.firstOrNull()?.tabRenderer?.content?.sectionListRenderer?.contents?.firstOrNull()?.gridRenderer?.items!!.drop(
            1
        ) // the first item is "create new playlist"
            .mapNotNull(GridRenderer.Item::musicTwoRowItemRenderer).mapNotNull {
                ArtistItemsPage.fromMusicTwoRowItemRenderer(it) as? PlaylistItem
            }
    }

    suspend fun player(videoId: String, playlistId: String? = null): Result<PlayerResponse?> =
        runCatching {
            var playerResponse: PlayerResponse?
            if (this.cookie != null) { // if logged in: try ANDROID_MUSIC client first because IOS client does not play age restricted songs
                playerResponse = innerTube.player(ANDROID_MUSIC, videoId, playlistId).body()
                if (playerResponse?.playabilityStatus?.status == "OK") {
                    return@runCatching playerResponse
                }
            }
            playerResponse = innerTube.player(IOS, videoId, playlistId).body()
            if (playerResponse?.playabilityStatus?.status == "OK") {
                return@runCatching playerResponse
            }
            val safePlayerResponse = innerTube.player(TVHTML5, videoId, playlistId).body()
            if (safePlayerResponse?.playabilityStatus?.status != "OK") {
                return@runCatching playerResponse
            }
            val audioStreams = innerTube.pipedStreams(videoId).body()?.audioStreams
            safePlayerResponse.copy(
                streamingData = safePlayerResponse.streamingData?.copy(
                    adaptiveFormats = safePlayerResponse.streamingData.adaptiveFormats.mapNotNull { adaptiveFormat ->
                        audioStreams?.find { it.bitrate == adaptiveFormat.bitrate }?.let {
                            adaptiveFormat.copy(
                                url = it.url
                            )
                        }
                    })
            )
        }

    suspend fun next(endpoint: WatchEndpoint, continuation: String? = null): Result<NextResult> =
        runCatching {
            val response = innerTube.next(
                WEB_REMIX,
                endpoint.videoId,
                endpoint.playlistId,
                endpoint.playlistSetVideoId,
                endpoint.index,
                endpoint.params,
                continuation
            )
            val title =
                response.body()?.contents?.singleColumnMusicWatchNextResultsRenderer?.tabbedRenderer?.watchNextTabbedResultsRenderer?.tabs[0]?.tabRenderer?.content?.musicQueueRenderer?.header?.musicQueueHeaderRenderer?.subtitle?.runs?.firstOrNull()?.text
            val playlistPanelRenderer =
                response.body()?.continuationContents?.playlistPanelContinuation
                    ?: response.body()?.contents?.singleColumnMusicWatchNextResultsRenderer?.tabbedRenderer?.watchNextTabbedResultsRenderer?.tabs[0]?.tabRenderer?.content?.musicQueueRenderer?.content?.playlistPanelRenderer

            val items = playlistPanelRenderer?.contents?.mapNotNull { content ->
                content.playlistPanelVideoRenderer?.let(NextPage::fromPlaylistPanelVideoRenderer)
                    ?.let { it to content.playlistPanelVideoRenderer.selected }
            }
            val songs = items?.map { it.first }
            val currentIndex = items?.indexOfFirst { it.second }.takeIf { it != -1 }

            // load automix items
            playlistPanelRenderer?.contents?.lastOrNull()?.automixPreviewVideoRenderer?.content?.automixPlaylistVideoRenderer?.navigationEndpoint?.watchPlaylistEndpoint?.let { watchPlaylistEndpoint ->
                return@runCatching next(watchPlaylistEndpoint).getOrThrow().let { result ->
                    result.copy(
                        title = title,
                        items = songs?.plus(result.items).orEmpty(),
                        lyricsEndpoint = response.body()?.contents?.singleColumnMusicWatchNextResultsRenderer?.tabbedRenderer?.watchNextTabbedResultsRenderer?.tabs?.getOrNull(
                            1
                        )?.tabRenderer?.endpoint?.browseEndpoint,
                        relatedEndpoint = response.body()?.contents?.singleColumnMusicWatchNextResultsRenderer?.tabbedRenderer?.watchNextTabbedResultsRenderer?.tabs?.getOrNull(
                            2
                        )?.tabRenderer?.endpoint?.browseEndpoint,
                        currentIndex = currentIndex,
                        endpoint = watchPlaylistEndpoint
                    )
                }
            }
            NextResult(
                title = title,
                items = songs.orEmpty(),
                currentIndex = currentIndex,
                lyricsEndpoint = response.body()?.contents?.singleColumnMusicWatchNextResultsRenderer?.tabbedRenderer?.watchNextTabbedResultsRenderer?.tabs?.getOrNull(
                    1
                )?.tabRenderer?.endpoint?.browseEndpoint,
                relatedEndpoint = response.body()?.contents?.singleColumnMusicWatchNextResultsRenderer?.tabbedRenderer?.watchNextTabbedResultsRenderer?.tabs?.getOrNull(
                    2
                )?.tabRenderer?.endpoint?.browseEndpoint,
                continuation = playlistPanelRenderer?.continuations?.getContinuation(),
                endpoint = endpoint
            )
        }

    suspend fun lyrics(endpoint: BrowseEndpoint): Result<String?> = runCatching {
        val response = innerTube.browse(WEB_REMIX, endpoint.browseId, endpoint.params)
        response.body()?.contents?.sectionListRenderer?.contents?.firstOrNull()?.musicDescriptionShelfRenderer?.description?.runs?.firstOrNull()?.text
    }

    suspend fun related(endpoint: BrowseEndpoint): Result<RelatedPage> = runCatching {
        val response = innerTube.browse(WEB_REMIX, endpoint.browseId)
        val songs = mutableListOf<SongItem>()
        val albums = mutableListOf<AlbumItem>()
        val artists = mutableListOf<ArtistItem>()
        val playlists = mutableListOf<PlaylistItem>()
        response.body()?.contents?.sectionListRenderer?.contents?.forEach { sectionContent ->
            sectionContent.musicCarouselShelfRenderer?.contents?.forEach { content ->
                when (val item =
                    content.musicResponsiveListItemRenderer?.let(RelatedPage.Companion::fromMusicResponsiveListItemRenderer)
                        ?: content.musicTwoRowItemRenderer?.let(RelatedPage.Companion::fromMusicTwoRowItemRenderer)) {
                    is SongItem -> if (content.musicResponsiveListItemRenderer?.overlay?.musicItemThumbnailOverlayRenderer?.content?.musicPlayButtonRenderer?.playNavigationEndpoint?.watchEndpoint?.watchEndpointMusicSupportedConfigs?.watchEndpointMusicConfig?.musicVideoType == MUSIC_VIDEO_TYPE_ATV) songs.add(
                        item
                    )

                    is AlbumItem -> albums.add(item)
                    is ArtistItem -> artists.add(item)
                    is PlaylistItem -> playlists.add(item)
                    null -> {}
                }
            }
        }
        RelatedPage(songs, albums, artists, playlists)
    }

    suspend fun queue(
        videoIds: List<String>? = null, playlistId: String? = null
    ): Result<List<SongItem>> = runCatching {
        if (videoIds != null) {
            assert(videoIds.size <= MAX_GET_QUEUE_SIZE) // Max video limit
        }

        innerTube.getQueue(WEB_REMIX, videoIds, playlistId).body()?.queueDatas?.mapNotNull {
            it.content.playlistPanelVideoRenderer?.let { renderer ->
                NextPage.fromPlaylistPanelVideoRenderer(renderer)
            }
        }.orEmpty()
    }

    suspend fun transcript(videoId: String): Result<String> = runCatching {
        val response = innerTube.getTranscript(WEB, videoId)
        response.body()?.actions?.firstOrNull()?.updateEngagementPanelAction?.content?.transcriptRenderer?.body?.transcriptBodyRenderer?.cueGroups?.joinToString(
            separator = "\n"
        ) { group ->
            val time = group.transcriptCueGroupRenderer.cues[0].transcriptCueRenderer.startOffsetMs
            val text =
                group.transcriptCueGroupRenderer.cues[0].transcriptCueRenderer.cue.simpleText.trim(
                    '♪'
                ).trim(' ')
            "[%02d:%02d.%03d]$text".format(time / 60000, (time / 1000) % 60, time % 1000)
        }!!
    }

    suspend fun extractVisitorData(innerTube: InnerTube): Result<String?> {
        val response = innerTube.getSwJsData()
        return runCatching {
            if (!response.isSuccessful) {
                throw Exception("Failed to fetch sw.js_data: ${response.message()}")
            }

            val jsonString =
                response.body()?.substring(5) ?: throw Exception("Response body is null")
            val jsonArray = JsonParser.parseString(jsonString).asJsonArray

            jsonArray[0].asJsonArray[2].asJsonArray.first {
                it.isJsonPrimitive && it.asString.startsWith(
                    VISITOR_DATA_PREFIX
                )
            }.asString
        }
    }

    suspend fun accountInfo(): Result<AccountInfo?> = runCatching {
        innerTube.accountMenu(WEB_REMIX)
            .body()?.actions[0]?.openPopupAction?.popup?.multiPageMenuRenderer?.header?.activeAccountHeaderRenderer?.toAccountInfo()
    }

    @JvmInline
    value class SearchFilter(val value: String) {
        companion object {
            val FILTER_SONG = SearchFilter("EgWKAQIIAWoKEAkQBRAKEAMQBA%3D%3D")
            val FILTER_VIDEO = SearchFilter("EgWKAQIQAWoKEAkQChAFEAMQBA%3D%3D")
            val FILTER_ALBUM = SearchFilter("EgWKAQIYAWoKEAkQChAFEAMQBA%3D%3D")
            val FILTER_ARTIST = SearchFilter("EgWKAQIgAWoKEAkQChAFEAMQBA%3D%3D")
            val FILTER_FEATURED_PLAYLIST = SearchFilter("EgeKAQQoADgBagwQDhAKEAMQBRAJEAQ%3D")
            val FILTER_COMMUNITY_PLAYLIST = SearchFilter("EgeKAQQoAEABagoQAxAEEAoQCRAF")
        }
    }

    const val MAX_GET_QUEUE_SIZE = 1000

    private const val VISITOR_DATA_PREFIX = "Cgt"

    const val DEFAULT_VISITOR_DATA = "CgtsZG1ySnZiQWtSbyiMjuGSBg%3D%3D"
}
