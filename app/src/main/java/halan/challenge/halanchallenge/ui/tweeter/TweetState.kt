package halan.challenge.halanchallenge.ui.tweeter

// State for the View
data class TweetState(
    val tweetText: String = "",
    val charactersTyped: Int = 0,
    val charactersRemaining: Int = 280,
    val isTweetReady: Boolean = false,
    val snackBarEvent: String? = null,
    val loading: Boolean = false
)
