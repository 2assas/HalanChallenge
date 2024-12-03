package halan.challenge.domain.repositories

interface TweetRepository {
    suspend fun postTweet(tweet: String): Result<Unit>
}