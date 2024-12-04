package halan.challenge.halanchallenge.ui.tweeter.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import halan.challenge.halanchallenge.ui.tweeter.TweetState

@Composable
fun TweetText(state: TweetState, onTextChanged: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(239.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
    ) {
        BasicTextField(value = state.tweetText,
            onValueChange = {
                onTextChanged(it)  // Pass the new text value to the ViewModel
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            decorationBox = { innerTextField ->
                if (state.tweetText.isEmpty()) {
                    Text(
                        "Start typing! You can enter up to 280 characters",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                innerTextField()
            })
    }
}

