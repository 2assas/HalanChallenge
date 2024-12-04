package halan.challenge.data.utils


import okio.IOException
import retrofit2.HttpException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
    return try {
        apiCall()
    } catch (exception: Exception) {
        throw when (exception) {
            is HttpException -> AppException.ApiException(exception.code(), exception.message())
            is IOException -> AppException.NetworkException(exception.message)
            else -> AppException.UnknownException(exception.message)
        }
    }
}