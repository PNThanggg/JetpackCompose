package com.apps.youtube.api.datastore.repository

import com.apps.youtube.api.datastore.models.ApplicationPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    /**
     * Stream of [com.apps.youtube.api.datastore.models.ApplicationPreferences].
     */
    val applicationPreferences: Flow<ApplicationPreferences>


    suspend fun updateApplicationPreferences(
        transform: suspend (ApplicationPreferences) -> ApplicationPreferences,
    )
}
