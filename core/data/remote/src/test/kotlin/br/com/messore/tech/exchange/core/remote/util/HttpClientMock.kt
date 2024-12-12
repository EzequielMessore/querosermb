package br.com.messore.tech.exchange.core.remote.util

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpResponseData
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

const val MIME_JSON = "application/json"

data class HttpClientMock(
    private val engine: HttpClientEngine,
) {
    val httpClient = HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        defaultRequest {
            header("X-CoinAPI-Key", "API_KEY")
        }
    }
}

inline fun <reified T> T.loadJson(file: String): String? =
    T::class.java.classLoader?.getResource("json/$file")?.readText()

fun MockRequestHandleScope.respondSuccess(
    content: String = "",
): HttpResponseData =
    respond(content, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, MIME_JSON))
