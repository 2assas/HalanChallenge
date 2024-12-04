import halan.challenge.data.dataSource.remote.auth.TwitterAuthConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import okio.ByteString
import java.io.IOException
import java.net.URLEncoder
import java.security.GeneralSecurityException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


class Oauth1SigningInterceptor(val getOauthKeys: () -> TwitterAuthConfig,
                               val nonce: String = UUID.randomUUID().toString(),
                               val timestamp: Long = System.currentTimeMillis() / 1000L) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(signRequest(chain.request()))
    }

    @Throws(IOException::class)
    fun signRequest(request: Request): Request {
        val keys = getOauthKeys()

        //Setup default parameters that will be sent with authorization header
        val parameters = hashMapOf(
            OAUTH_CONSUMER_KEY to keys.consumerKey,
            OAUTH_NONCE to nonce,
            OAUTH_SIGNATURE_METHOD to OAUTH_SIGNATURE_METHOD_VALUE,
            OAUTH_TIMESTAMP to timestamp.toString(),
            OAUTH_VERSION to OAUTH_VERSION_VALUE
        )
        parameters[OAUTH_TOKEN] = keys.accessToken

        //Copy query parameters into param map
        val url = request.url
        for (i in 0 until url.querySize) {
            val name = url.queryParameterName(i)
            val value = url.queryParameterValue(i)
            if (value != null) {
                parameters[name] = value
            }
        }

        //Copy form body into param map
        request.body?.let { it ->
            it.asString().split('&')
                .takeIf { it.isNotEmpty() }
                ?.map { it.split('=', limit = 2) }
                ?.filter {
                    (it.size == 2).also { hasTwoParts ->
                        if (!hasTwoParts) throw IllegalStateException("Key with no value: ${it.getOrNull(0)}")
                    }
                }
                ?.associate {
                    val (key, value) = it
                    key to value
                }
                ?.also { parameters.putAll(it) }
        }

        //Create signature
        val method = request.method.encodeUtf8()
        val baseUrl = request.url.newBuilder().query(null).build().toString().encodeUtf8()
        val signingKey = "${keys.consumerSecret.encodeUtf8()}&${keys.tokenSecret?.encodeUtf8()
            ?: ""}"
        val params = parameters.encodeForSignature()
        val dataToSign = "$method&$baseUrl&$params"
        parameters[OAUTH_SIGNATURE] = sign(signingKey, dataToSign).encodeUtf8()

        //Create auth header
        val authHeader = "OAuth ${parameters.toHeaderFormat()}"
        return request.newBuilder().addHeader("Authorization", authHeader).build()
    }

    private fun RequestBody.asString() = Buffer().run {
        writeTo(this)
        readUtf8().replace("+", "%2B")
    }

    @Throws(GeneralSecurityException::class)
    private fun sign(key: String, data: String): String {
        val secretKey = SecretKeySpec(key.toBytesUtf8(), "HmacSHA1")
        val macResult = Mac.getInstance("HmacSHA1").run {
            init(secretKey)
            doFinal(data.toBytesUtf8())
        }
        return ByteString.of(*macResult).base64()
    }

    private fun String.toBytesUtf8() = this.toByteArray()

    private fun HashMap<String, String>.toHeaderFormat() =
        filter { it.key in baseKeys }
            .toList()
            .sortedBy { (key, _) -> key }
            .toMap()
            .map { "${it.key}=\"${it.value}\"" }
            .joinToString(", ")


    private fun HashMap<String, String>.encodeForSignature() =
        toList()
            .sortedBy { (key, _) -> key }
            .toMap()
            .map { "${it.key}=${it.value}" }
            .joinToString("&")
            .encodeUtf8()

    private fun String.encodeUtf8() = URLEncoder.encode(this, "UTF-8").replace("+", "%2B")

    companion object {
        private const val OAUTH_CONSUMER_KEY = "oauth_consumer_key"
        private const val OAUTH_NONCE = "oauth_nonce"
        private const val OAUTH_SIGNATURE = "oauth_signature"
        private const val OAUTH_SIGNATURE_METHOD = "oauth_signature_method"
        private const val OAUTH_SIGNATURE_METHOD_VALUE = "HMAC-SHA1"
        private const val OAUTH_TIMESTAMP = "oauth_timestamp"
        private const val OAUTH_TOKEN = "oauth_token"
        private const val OAUTH_VERSION = "oauth_version"
        private const val OAUTH_VERSION_VALUE = "1.0"

        private val baseKeys = arrayListOf(
            OAUTH_CONSUMER_KEY,
            OAUTH_NONCE,
            OAUTH_SIGNATURE,
            OAUTH_SIGNATURE_METHOD,
            OAUTH_TIMESTAMP,
            OAUTH_TOKEN,
            OAUTH_VERSION)
    }
}