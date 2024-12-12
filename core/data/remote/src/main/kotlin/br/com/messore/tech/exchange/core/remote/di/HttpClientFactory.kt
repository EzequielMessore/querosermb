package br.com.messore.tech.exchange.core.remote.di

import br.com.messore.tech.exchange.core.domain.config.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val X_API_KEY = "X-CoinAPI-Key"

fun createHttpClient(
    apiConfig: ApiConfig,
): HttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
    defaultRequest {
        url(apiConfig.url)
        header(X_API_KEY, apiConfig.apiKey)
        contentType(ContentType.Application.Json)
    }
}
