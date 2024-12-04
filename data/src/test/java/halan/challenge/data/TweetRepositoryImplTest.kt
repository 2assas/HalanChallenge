import halan.challenge.data.dataSource.remote.TweetRemoteDataSource
import halan.challenge.data.models.TweetRequestBody
import halan.challenge.data.repository.TweetRepositoryImpl
import halan.challenge.domain.repositories.TweetRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class TweetRepositoryImplTest {

    @Mock
    private lateinit var tweetRepository: TweetRepository

    @Mock
    private lateinit var remoteDataSource: TweetRemoteDataSource

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        tweetRepository = TweetRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `test postTweet success`() = runTest {
        // Given
        val tweet = "Test tweet"
        val tweetRequestBody = TweetRequestBody(tweet)

        // Mocking the response to simulate a successful API call
        val response = Response.success(Unit) // mock a successful response
        Mockito.`when`(remoteDataSource.postTweet(tweetRequestBody))
            .thenReturn(response)

        // When
        val result = tweetRepository.postTweet(tweet)

        // Then
        assertTrue(result.isSuccess) // Ensure the result is a success
        Mockito.verify(remoteDataSource).postTweet(tweetRequestBody) // Verify the mocked method is called
    }

    @Test
    fun `test postTweet failure`() = runTest {
        // Given
        val tweetRequestBody = TweetRequestBody("Test tweet")
        val exception = RuntimeException("API error") // Use RuntimeException instead of checked Exception

        // Mocking the remote data source to throw a RuntimeException
        Mockito.`when`(remoteDataSource.postTweet(tweetRequestBody)).thenThrow(exception)

        // When
        val result = tweetRepository.postTweet("Test tweet") // This should return a Result.failure

        // Then
        assertTrue(result.isFailure) // Ensure the result is a failure
        assertEquals("API error", result.exceptionOrNull()?.message) // Ensure the exception message is correct
    }

}
