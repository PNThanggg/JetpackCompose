package jc.app.bank_pick.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource<T> {

    val preferences: Flow<T>

    suspend fun update(transform: suspend (T) -> T)
}
