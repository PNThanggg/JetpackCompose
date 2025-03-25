package jc.apps.lol.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jc.apps.lol.data.repository.ApiRepositoryImpl
import jc.apps.lol.domain.repository.ApiRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindApiRepository(
        apiRepositoryImpl: ApiRepositoryImpl,
    ): ApiRepository
}