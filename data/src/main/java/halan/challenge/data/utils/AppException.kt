package halan.challenge.data.utils

sealed class AppException(message: String?) : Exception(message) {
    class ApiException(val code: Int, message: String?) : AppException(message)
    class NetworkException(message: String?) : AppException(message)
    class UnknownException(message: String?) : AppException(message)
}