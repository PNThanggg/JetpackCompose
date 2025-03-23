package jc.apps.clean_architecture.store.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jc.apps.clean_architecture.store.data.repository.ProductsRepositoryImpl
import jc.apps.clean_architecture.store.domain.repository.ProductsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreDI {

    @Binds
    @Singleton
    abstract fun provideProductsRepository(
        repository: ProductsRepositoryImpl
    ): ProductsRepository
}