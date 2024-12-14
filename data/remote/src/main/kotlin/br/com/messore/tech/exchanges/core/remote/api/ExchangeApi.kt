package br.com.messore.tech.exchanges.core.remote.api

import br.com.messore.tech.exchanges.core.remote.model.Exchange
import br.com.messore.tech.exchanges.core.remote.model.Image
import br.com.messore.tech.exchanges.core.remote.service.ExchangeService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

internal class ExchangeApi(
    private val httpClient: HttpClient,
) : ExchangeService {
    override suspend fun getExchanges(): List<Exchange> {
        return httpClient.get {
            url {
                path("exchanges")
            }
        }.body()
    }

    override suspend fun getExchangesImages(): List<Image> {
        return httpClient.get {
            url {
                path("exchanges/icons/64")
            }
        }.body()
    }

    override suspend fun getExchangeById(id: String): Exchange {
        return httpClient.get {
            url {
                path("exchanges")
                parameter("filter_exchange_id", id)
            }
        }.body<List<Exchange>>().first()
    }
}
