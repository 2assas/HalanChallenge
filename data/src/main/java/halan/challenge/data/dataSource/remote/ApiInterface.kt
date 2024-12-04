package halan.challenge.data.dataSource.remote

import halan.challenge.data.models.TweetRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("tweets")
    suspend fun postTweet(@Body tweet: TweetRequestBody): Response<Unit>
}