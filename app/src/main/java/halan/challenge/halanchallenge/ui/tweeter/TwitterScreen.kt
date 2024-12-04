package halan.challenge.halanchallenge.ui.tweeter

import android.content.Context
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import halan.challenge.halanchallenge.R
import halan.challenge.halanchallenge.ui.tweeter.components.ActionButtons
import halan.challenge.halanchallenge.ui.tweeter.components.AppBar
import halan.challenge.halanchallenge.ui.tweeter.components.Counters
import halan.challenge.halanchallenge.ui.tweeter.components.TweetText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwitterScreen(
    viewModel: TwitterViewModel = hiltViewModel(), navController: NavController
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }  // To show Snackbar
    val keyboardController = LocalSoftwareKeyboardController.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    LaunchedEffect(state.snackBarEvent) {
        state.snackBarEvent?.let {
            snackBarHostState.showSnackbar(
                message = it,
                duration = SnackbarDuration.Short,
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            AppBar(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier.imePadding()
        ) {
            if (state.loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Twitter logo below the app bar, centered
                Icon(
                    painter = painterResource(id = R.drawable.twitter_logo),
                    tint = null,
                    contentDescription = "Twitter Icon",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Counters
                Counters(state)

                // Big rounded box for text field with hint
                TweetText(state) {
                    viewModel.onIntent(TweetIntent.TextChanged(it))
                }

                // Buttons row for Copy Text and Clear Text
                ActionButtons(state,
                    onCopyText = {
                        clipboardManager.setText(AnnotatedString(state.tweetText))
                    },
                    onClearText = { viewModel.onIntent(TweetIntent.ClearText) },
                    onPostTweet = {
                        viewModel.onIntent(TweetIntent.PostTweet)
                        keyboardController?.hide()
                    })
            }
        }
    }
}



