package halan.challenge.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import halan.challenge.data.dataSource.remote.ApiInterface
import halan.challenge.data.dataSource.remote.TweetRemoteDataSource
import halan.challenge.data.repository.TweetRepositoryImpl
import halan.challenge.domain.repositories.TweetRepository
import halan.challenge.domain.useCases.PostTweetUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TweetModule {

    @Provides
    @Singleton
    fun provideTweetRemoteDataSource(api: ApiInterface): TweetRemoteDataSource {
        return TweetRemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideTweetRepository(
        remoteDataSource: TweetRemoteDataSource
    ): TweetRepository {
        return TweetRepositoryImpl(remoteDataSource)
    }


    @Provides
    @Singleton
    fun providePostTweetUseCase(repository: TweetRepository): PostTweetUseCase {
        return PostTweetUseCase(repository)
    }


}