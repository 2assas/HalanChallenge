package halan.challenge.data.dataSource.remote

import halan.challenge.data.models.TweetRequestBody
import halan.challenge.data.utils.safeApiCall
import retrofit2.Response
import javax.inject.Inject

class TweetRemoteDataSource @Inject constructor(
    private val apiService: ApiInterface
) {
    suspend fun postTweet(tweet: TweetRequestBody): Response<Unit> {
        return safeApiCall { apiService.postTweet(tweet) }
    }
}