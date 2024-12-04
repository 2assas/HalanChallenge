package halan.challenge.halanchallenge.ui.tweeter

sealed class TweetIntent {
    data class TextChanged(val text: String) : TweetIntent()
    data object ClearText : TweetIntent()
    data object PostTweet : TweetIntent()
}
