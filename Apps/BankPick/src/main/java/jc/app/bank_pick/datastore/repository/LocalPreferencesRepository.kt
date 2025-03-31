package jc.app.bank_pick.datastore.repository

import jc.app.bank_pick.datastore.datasource.AppPreferencesDataSource
import jc.app.bank_pick.datastore.models.ApplicationPreferences
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
