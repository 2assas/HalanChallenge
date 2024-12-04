package halan.challenge.data.repository

import halan.challenge.data.dataSource.remote.TweetRemoteDataSource
import halan.challenge.data.models.TweetRequestBody
import halan.challenge.domain.repositories.TweetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TweetRepositoryImpl @Inject constructor(
    private val remoteDataSource: TweetRemoteDataSource
) : TweetRepository {
    override suspend fun postTweet(tweet: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val response = remoteDataSource.postTweet(TweetRequestBody(tweet))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(
                    Exception(
                        "Failed to post tweet due ${
                            response.message()
                        }"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

