package br.com.messore.tech.exchange.core.remote.source

import br.com.messore.tech.exchange.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchange.core.remote.service.ExchangeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class ExchangeDataSourceRemote(
    private val exchangeService: ExchangeService,
) : ExchangeDataSource.Remote {
    override suspend fun getExchanges(): List<Exchange> = withContext(Dispatchers.IO) {
        coroutineScope {
            val exchangesDeferred = async { exchangeService.getExchanges() }
            val imagesDeferred = async {
                runCatching { exchangeService.getExchangesImages() }
                    .getOrNull()
                    .orEmpty()
            }

            val images = imagesDeferred.await()
                .associateBy { it.exchangeId }

            exchangesDeferred.await()
                .map {
                    Exchange(
                        name = it.name.orEmpty(),
                        website = it.website.orEmpty(),
                        exchangeId = it.exchangeId.orEmpty(),
                        volume1DayUsd = it.volume1DayUsd ?: 0.0,
                        image = images[it.exchangeId]?.url.orEmpty(),
                    )
                }
        }
    }
}