package com.apps.youtube.api.datastore.repository

import com.apps.youtube.api.datastore.datasource.AppPreferencesDataSource
import com.apps.youtube.api.datastore.models.ApplicationPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalPreferencesRepository @Inject constructor(
    private val appPreferencesDataSource: AppPreferencesDataSource,
) : PreferencesRepository {
    override val applicationPreferences: Flow<ApplicationPreferences>
        get() = appPreferencesDataSource.preferences

    override suspend fun updateApplicationPreferences(
        transform: suspend (ApplicationPreferences) -> ApplicationPreferences,
    ) {
        appPreferencesDataSource.update(transform)
    }
}
