package com.apps.youtube.api.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.apps.youtube.api.datastore.models.ApplicationPreferences
import com.apps.youtube.api.datastore.serializer.ApplicationPreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import modules.common.di.ApplicationScope
import modules.common.di.IODispatcher
import javax.inject.Singleton

private const val APP_PREFERENCES_DATASTORE_FILE = "app_preferences.json"

/**
 * Hilt module for DataStore preferences
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        //todo handle exception
    }

    /**
     * Provides DataStore for application preferences
     * @param context Application context
     * @param ioDispatcher IO dispatcher for background operations
     * @param scope Application coroutine scope
     * @return DataStore for ApplicationPreferences
     */
    @Provides
    @Singleton
    fun provideAppPreferencesDataStore(
        @ApplicationContext context: Context,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
    ): DataStore<ApplicationPreferences> {
        return DataStoreFactory.create(
            serializer = ApplicationPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher + coroutineExceptionHandler),
            produceFile = { context.dataStoreFile(APP_PREFERENCES_DATASTORE_FILE) },
        )
    }
}
