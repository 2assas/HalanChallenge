package halan.challenge.halanchallenge.ui.tweeter.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import halan.challenge.halanchallenge.ui.tweeter.TweetState

@Composable
fun Counters(state: TweetState) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    2.dp, MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier
            ) {
                // Title for Characters Typed
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Characters Typed",
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp
                    )
                }
                // Value for Characters Typed
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${state.charactersTyped}/280",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    2.dp, MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center, modifier = Modifier
            ) {
                // Title for Characters Remaining
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Characters Remaining",
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 14.sp
                    )
                }
                // Value for Characters Remaining
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${state.charactersRemaining}",
                        style = MaterialTheme.typography.headlineLarge
                    )
                }
            }
        }
    }

}
