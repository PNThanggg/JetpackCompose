package modules.lib.lyrics.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import modules.lib.lyrics.data.datasource.LrcApiService
import modules.lib.lyrics.data.datasource.LrcRemoteDataSource
import modules.lib.lyrics.data.repository.LrcRepositoryImpl
import modules.lib.lyrics.domain.repository.LrcRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LrcModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl("https://lrclib.net").client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideLrcApiService(
        retrofit: Retrofit
    ): LrcApiService {
        return retrofit.create(LrcApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideLrcRemoteDataSource(
        apiService: LrcApiService
    ): LrcRemoteDataSource {
        return LrcRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideLrcRepository(
        remoteDataSource: LrcRemoteDataSource
    ): LrcRepository {
        return LrcRepositoryImpl(remoteDataSource)
    }
}