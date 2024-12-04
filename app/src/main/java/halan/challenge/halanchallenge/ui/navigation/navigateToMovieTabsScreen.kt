package halan.challenge.halanchallenge.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import banquemisr.challenge05.movies.ui.navigation.Destinations
import halan.challenge.halanchallenge.ui.tweeter.TwitterScreen

fun NavController.navigateToTwitterScreen() {
    this.navigate(Destinations.TWITTER_SCREEN)
}


fun NavGraphBuilder.twitterScreen(navController: NavController) {
    composable(Destinations.TWITTER_SCREEN) {
        TwitterScreen(navController = navController)
    }
}
