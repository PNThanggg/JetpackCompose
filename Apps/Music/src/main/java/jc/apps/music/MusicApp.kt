package jc.apps.music

import android.app.Application
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import dagger.hilt.android.HiltAndroidApp
import jc.apps.music.constants.MaxImageCacheSizeKey
import jc.apps.music.utils.dataStore
import jc.apps.music.utils.get

@HiltAndroidApp
class MusicApp : Application(), ImageLoaderFactory {
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this).crossfade(true).respectCacheHeaders(false)
            .allowHardware(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P).diskCache(
                DiskCache.Builder().directory(cacheDir.resolve("coil"))
                    .maxSizeBytes((dataStore[MaxImageCacheSizeKey] ?: 512) * 1024 * 1024L).build()
            ).build()
    }
}