package br.com.messore.tech.exchanges.core.remote.source

import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.model.Image
import br.com.messore.tech.exchanges.core.remote.mapper.toDomain
import br.com.messore.tech.exchanges.core.remote.service.ExchangeService

class ExchangeDataSourceRemote(
    private val exchangeService: ExchangeService,
) : ExchangeDataSource.Remote {
    override suspend fun getExchanges(): List<Exchange> {
        return exchangeService.getExchanges().map { it.toDomain() }
    }

    override suspend fun getImagesExchange(): List<Image> {
        return exchangeService.getExchangesImages().map { it.toDomain() }
    }

    override suspend fun getExchangeById(id: String): Exchange {
        return exchangeService.getExchangeById(id).toDomain()
    }
}
