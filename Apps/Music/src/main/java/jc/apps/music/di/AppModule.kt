package jc.apps.music.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.DatabaseProvider
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jc.apps.music.constants.MaxSongCacheSizeKey
import jc.apps.music.db.InternalDatabase
import jc.apps.music.db.MusicDatabase
import jc.apps.music.utils.dataStore
import jc.apps.music.utils.get
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MusicDatabase = InternalDatabase.newInstance(context)

    @OptIn(UnstableApi::class)
    @Singleton
    @Provides
    fun provideDatabaseProvider(
        @ApplicationContext context: Context
    ): DatabaseProvider = StandaloneDatabaseProvider(context)

    @OptIn(UnstableApi::class)
    @Singleton
    @Provides
    @PlayerCache
    fun providePlayerCache(
        @ApplicationContext context: Context, databaseProvider: DatabaseProvider
    ): SimpleCache {
        val constructor = {
            SimpleCache(
                context.filesDir.resolve("exoplayer"),
                when (val cacheSize = context.dataStore[MaxSongCacheSizeKey] ?: 1024) {
                    -1 -> NoOpCacheEvictor()
                    else -> LeastRecentlyUsedCacheEvictor(cacheSize * 1024 * 1024L)
                },
                databaseProvider
            )
        }
        constructor().release()
        return constructor()
    }

    @OptIn(UnstableApi::class)
    @Singleton
    @Provides
    @DownloadCache
    fun provideDownloadCache(
        @ApplicationContext context: Context, databaseProvider: DatabaseProvider
    ): SimpleCache {
        val constructor = {
            SimpleCache(context.filesDir.resolve("download"), NoOpCacheEvictor(), databaseProvider)
        }
        constructor().release()
        return constructor()
    }
}
