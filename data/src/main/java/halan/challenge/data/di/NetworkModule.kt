package halan.challenge.data.di


import Oauth1SigningInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import halan.challenge.data.dataSource.remote.ApiInterface
import halan.challenge.data.dataSource.remote.auth.TwitterAuthConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        oauth1SigningInterceptor: Oauth1SigningInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(oauth1SigningInterceptor) // Add OAuth interceptor
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Singleton
    fun provideOauth1SigningInterceptor(
        getOauthKeys: () -> TwitterAuthConfig
    ): Oauth1SigningInterceptor {
        return Oauth1SigningInterceptor(getOauthKeys = getOauthKeys)
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return TwitterAuthConfig.baseUrl
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}
