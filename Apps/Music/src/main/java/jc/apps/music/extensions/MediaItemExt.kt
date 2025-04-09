package jc.apps.music.extensions

import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata.MEDIA_TYPE_MUSIC
import androidx.media3.common.util.UnstableApi
import jc.apps.music.db.entities.Song
import jc.apps.music.models.MediaMetadata
import jc.apps.music.models.toMediaMetadata
import modules.innertube.models.SongItem

val MediaItem.metadata: MediaMetadata?
    get() = localConfiguration?.tag as? MediaMetadata

@OptIn(UnstableApi::class)
fun Song.toMediaItem() =
    MediaItem.Builder().setMediaId(song.id).setUri(song.id).setCustomCacheKey(song.id)
        .setTag(toMediaMetadata()).setMediaMetadata(
            androidx.media3.common.MediaMetadata.Builder().setTitle(song.title)
                .setSubtitle(artists.joinToString { it.name })
                .setArtist(artists.joinToString { it.name })
                .setArtworkUri(song.thumbnailUrl?.toUri()).setAlbumTitle(song.albumName)
                .setMediaType(MEDIA_TYPE_MUSIC).build()
        ).build()

@OptIn(UnstableApi::class)
fun SongItem.toMediaItem() =
    MediaItem.Builder().setMediaId(id).setUri(id).setCustomCacheKey(id).setTag(toMediaMetadata())
        .setMediaMetadata(
            androidx.media3.common.MediaMetadata.Builder().setTitle(title)
                .setSubtitle(artists.joinToString { it.name })
                .setArtist(artists.joinToString { it.name }).setArtworkUri(thumbnail.toUri())
                .setAlbumTitle(album?.name).setMediaType(MEDIA_TYPE_MUSIC).build()
        ).build()

@OptIn(UnstableApi::class)
fun MediaMetadata.toMediaItem() =
    MediaItem.Builder().setMediaId(id).setUri(id).setCustomCacheKey(id).setTag(this)
        .setMediaMetadata(
            androidx.media3.common.MediaMetadata.Builder().setTitle(title)
                .setSubtitle(artists.joinToString { it.name })
                .setArtist(artists.joinToString { it.name }).setArtworkUri(thumbnailUrl?.toUri())
                .setAlbumTitle(album?.title).setMediaType(MEDIA_TYPE_MUSIC).build()
        ).build()