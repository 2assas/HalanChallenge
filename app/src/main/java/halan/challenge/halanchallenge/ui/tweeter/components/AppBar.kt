package halan.challenge.halanchallenge.ui.tweeter.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import halan.challenge.halanchallenge.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController) {
    TopAppBar(title = {
        Text(
            "Twitter character count",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    },
        navigationIcon = {},
        actions = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                    contentDescription = "Back"
                )
            }
        })
}
