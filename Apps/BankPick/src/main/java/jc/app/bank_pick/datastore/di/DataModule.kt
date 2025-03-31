package jc.app.bank_pick.datastore.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jc.app.bank_pick.datastore.repository.LocalPreferencesRepository
import jc.app.bank_pick.datastore.repository.PreferencesRepository

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsPreferencesRepository(
        preferencesRepository: LocalPreferencesRepository,
    ): PreferencesRepository
}
