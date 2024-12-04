package halan.challenge.data.dataSource.remote.auth

import halan.challenge.data.BuildConfig

data class TwitterAuthConfig(
     val baseUrl: String = BuildConfig.BASE_URL,
     val consumerKey: String = BuildConfig.CONSUMER_KEY,
     val consumerSecret: String = BuildConfig.CONSUMER_SECRET,
     val accessToken: String = BuildConfig.ACCESS_TOKEN,
     val tokenSecret: String = BuildConfig.TOKEN_SECRET,
) {
     companion object {
          // Factory method to create an instance of TwitterAuthConfig using BuildConfig
          fun fromBuildConfig(): TwitterAuthConfig {
               return TwitterAuthConfig(
                    baseUrl = BuildConfig.BASE_URL,
                    consumerKey = BuildConfig.CONSUMER_KEY,
                    consumerSecret = BuildConfig.CONSUMER_SECRET,
                    accessToken = BuildConfig.ACCESS_TOKEN,
                    tokenSecret = BuildConfig.TOKEN_SECRET
               )
          }
     }
}
