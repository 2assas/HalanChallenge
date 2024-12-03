package halan.challenge.domain.useCases

import halan.challenge.domain.repositories.TweetRepository

class PostTweetUseCase(private val tweetRepository: TweetRepository) {

    suspend fun execute(tweet: String): Result<Unit> {
        return tweetRepository.postTweet(tweet)
    }
}