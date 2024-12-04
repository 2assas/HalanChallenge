package halan.challenge.data.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import halan.challenge.data.BuildConfig.ACCESS_TOKEN
import halan.challenge.data.BuildConfig.CONSUMER_KEY
import halan.challenge.data.BuildConfig.CONSUMER_SECRET
import halan.challenge.data.BuildConfig.TOKEN_SECRET
import halan.challenge.data.dataSource.remote.ApiInterface
import halan.challenge.data.dataSource.remote.auth.TwitterAuthConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer
import se.akerfeldt.okhttp.signpost.SigningInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
    ): OkHttpClient {
        val consumer = OkHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET)
        consumer.setTokenWithSecret(ACCESS_TOKEN, TOKEN_SECRET)

        return OkHttpClient.Builder()
            .addInterceptor(SigningInterceptor(consumer))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return TwitterAuthConfig.fromBuildConfig().baseUrl
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
