package com.apps.youtube.api.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource<T> {
    val preferences: Flow<T>

    suspend fun update(transform: suspend (T) -> T)
}
