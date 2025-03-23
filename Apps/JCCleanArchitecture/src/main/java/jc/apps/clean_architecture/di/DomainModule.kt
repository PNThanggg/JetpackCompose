package jc.apps.clean_architecture.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jc.apps.clean_architecture.BuildConfig
import jc.apps.clean_architecture.store.data.remote.ProductsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder().addInterceptor(logging).connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build()
    }

    @Singleton
    @Provides
    fun provideProductsApi(client: OkHttpClient): ProductsApi {
        return Retrofit.Builder().baseUrl("https://fakestoreapi.com/").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ProductsApi::class.java)
    }
}