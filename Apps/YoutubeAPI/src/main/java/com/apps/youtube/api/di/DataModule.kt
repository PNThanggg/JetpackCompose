package com.apps.youtube.api.di

import com.apps.youtube.api.data.repository.YoutubeApiRepositoryImpl
import com.apps.youtube.api.domain.repository.YoutubeApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindApiRepository(
        youtubeApiRepositoryImpl: YoutubeApiRepositoryImpl,
    ): YoutubeApiRepository
}