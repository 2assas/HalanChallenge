package halan.challenge.halanchallenge.ui.tweeter

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import halan.challenge.domain.repositories.TweetRepository
import halan.challenge.domain.useCases.CharacterCountUseCase
import halan.challenge.domain.useCases.PostTweetUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.logging.Handler
import javax.inject.Inject

@HiltViewModel
class TwitterViewModel @Inject constructor(
    private val characterCountUseCase: CharacterCountUseCase,
    private val postTweetUseCase: PostTweetUseCase
) : ViewModel() {

    private val _tweetResponse = MutableLiveData<Result<Unit>>()

    // Handle intents
    fun onIntent(intent: TweetIntent) {
        when (intent) {
            is TweetIntent.TextChanged -> handleTextChanged(intent.text)
            is TweetIntent.ClearText -> handleClearText()
            is TweetIntent.PostTweet -> handlePostTweet()
        }
    }

    //   StateFlow to manage the current state of the UI
    private val _state = MutableStateFlow(TweetState())
    val state: StateFlow<TweetState> get() = _state

    // Action to update the text and calculate character counts
    private fun handleTextChanged(text: String) {
        val charactersTyped = characterCountUseCase.calculateCharacterCount(text)
        val charactersRemaining = characterCountUseCase.calculateRemainingCharacters(text)
        val isTweetReady = characterCountUseCase.isTweetValid(text)

        _state.value = _state.value.copy(
            tweetText = text,
            charactersTyped = charactersTyped,
            charactersRemaining = charactersRemaining,
            isTweetReady = isTweetReady
        )
    }

    private fun handleClearText() {
        initState()
    }

    private fun clearSnackBar() {
        viewModelScope.launch {
            // Wait for a certain time before clearing the snackbar event
            delay(3000) // Delay for 3 seconds (adjust as necessary)
            _state.value = _state.value.copy(snackBarEvent = null) // Clear the snackbar
        }
    }

    // Function to post a tweet
    private fun handlePostTweet() {
        viewModelScope.launch {
            showLoading()
            val result = postTweetUseCase.execute(state.value.tweetText)
            _tweetResponse.value = result

            // Log the result
            if (result.isSuccess) {
                _state.value =
                    _state.value.copy(
                        snackBarEvent = "The tweet has been posted successfully",
                        loading = false
                    )
                initState()
            } else {
                Log.e(
                    "TwitterViewModel",
                    "Failed to post tweet: ${result.exceptionOrNull()?.localizedMessage}"
                )
                _state.value =
                    _state.value.copy(snackBarEvent = "Failed to post tweet", loading = false)
                clearSnackBar()
            }
        }
    }

    private fun showLoading() {
        _state.value = _state.value.copy(loading = true)
    }

    private fun initState() {
        _state.value = _state.value.copy(
            tweetText = "",
            charactersTyped = 0,
            charactersRemaining = 280,
            isTweetReady = false,
            snackBarEvent = null
        )
    }
}
