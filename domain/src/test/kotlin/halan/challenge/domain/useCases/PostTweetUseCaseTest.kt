package halan.challenge.domain.useCases

import halan.challenge.domain.repositories.TweetRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

import org.mockito.Mockito
import org.mockito.Mockito.`when`

class PostTweetUseCaseTest {

    private val mockRepository = Mockito.mock(TweetRepository::class.java)
    private val useCase = PostTweetUseCase(mockRepository)

    @Test
    fun `execute returns success when tweet is posted successfully`() = runTest {
        // Arrange
        val tweet = "This is a valid tweet"
        `when`(mockRepository.postTweet(tweet)).thenReturn(Result.success(Unit))

        // Act
        val result = useCase.execute(tweet)

        // Assert
        assertEquals(Result.success(Unit), result)
    }

    @Test
    fun `execute returns failure when tweet posting fails due to network error`() = runTest {
        // Arrange
        val tweet = "This is a valid tweet"
        val error = Exception("Network error")
        `when`(mockRepository.postTweet(tweet)).thenReturn(Result.failure(error))

        // Act
        val result = useCase.execute(tweet)

        // Assert
        assertEquals(Result.failure<Unit>(error), result)
    }

    @Test
    fun `execute returns failure when tweet exceeds character limit`() = runTest {
        // Arrange
        val tweet = "A".repeat(281) // 281 characters, exceeds limit
        val error = Exception("Tweet exceeds character limit")
        `when`(mockRepository.postTweet(tweet)).thenReturn(Result.failure(error))

        // Act
        val result = useCase.execute(tweet)

        // Assert
        assertEquals(Result.failure<Unit>(error), result)
    }

    @Test
    fun `execute returns failure when tweet is empty`() = runTest {
        // Arrange
        val tweet = ""
        val error = Exception("Tweet cannot be empty")
        `when`(mockRepository.postTweet(tweet)).thenReturn(Result.failure(error))

        // Act
        val result = useCase.execute(tweet)

        // Assert
        assertEquals(Result.failure<Unit>(error), result)
    }

    @Test
    fun `execute returns failure when tweet contains invalid characters`() = runTest {
        // Arrange
        val tweet = "This tweet contains invalid characters: ☠️"
        val error = Exception("Tweet contains invalid characters")
        `when`(mockRepository.postTweet(tweet)).thenReturn(Result.failure(error))

        // Act
        val result = useCase.execute(tweet)

        // Assert
        assertEquals(Result.failure<Unit>(error), result)
    }
}
