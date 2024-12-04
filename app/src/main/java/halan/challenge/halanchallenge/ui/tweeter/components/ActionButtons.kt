package halan.challenge.halanchallenge.ui.tweeter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import halan.challenge.halanchallenge.ui.tweeter.TweetState

@Composable
fun ActionButtons(
    state: TweetState, onCopyText: () -> Unit, onClearText: () -> Unit, onPostTweet: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) { }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onCopyText,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary),
            modifier = Modifier.padding(end = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Copy Text", color = Color.White, style = MaterialTheme.typography.bodyLarge
            )
        }
        Button(
            onClick = onClearText,
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(start = 8.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Clear Text", color = Color.White, style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    // Main Button to post tweet
    Button(
        onClick = onPostTweet,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = state.isTweetReady,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            "Post Tweet", color = Color.White, style = MaterialTheme.typography.titleLarge
        )
    }
}