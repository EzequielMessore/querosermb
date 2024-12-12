package br.com.messore.tech.exchanges.core.remote.source

import br.com.messore.tech.exchanges.core.remote.service.ExchangeService
import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.model.Image

class ExchangeDataSourceRemote(
    private val exchangeService: ExchangeService,
) : ExchangeDataSource.Remote {
    override suspend fun getExchanges(): List<Exchange> {
        return exchangeService.getExchanges().map {
            Exchange(
                name = it.name.orEmpty(),
                website = it.website.orEmpty(),
                exchangeId = it.exchangeId.orEmpty(),
                volume1DayUsd = it.volume1DayUsd ?: 0.0,
            )
        }
    }

    override suspend fun getImagesExchange(): List<Image> {
        return exchangeService.getExchangesImages().map {
            Image(
                url = it.url.orEmpty(),
                exchangeId = it.exchangeId.orEmpty(),
            )
        }
    }
}