package br.com.messore.tech.exchanges.core.remote.api

import br.com.messore.tech.exchanges.core.remote.model.Exchange
import br.com.messore.tech.exchanges.core.remote.model.Image
import br.com.messore.tech.exchanges.core.remote.service.ExchangeService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path

internal class ExchangeApi(
    private val httpClient: HttpClient,
) : ExchangeService {
    override suspend fun getExchanges(): List<Exchange> {
        return httpClient.get {
            url {
                path("exchanges")
            }
        }.body<List<Exchange>>()
    }

    override suspend fun getExchangesImages(): List<Image> {
        return httpClient.get {
            url {
                path("exchanges/icons/64")
            }
        }.body<List<Image>>()
    }
}