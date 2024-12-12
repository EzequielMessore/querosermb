package br.com.messore.tech.exchanges.core.data.repository

import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.repository.ExchangeRepository

class ExchangeRepositoryImpl(
    private val remoteDataSource: ExchangeDataSource.Remote,
): ExchangeRepository {
    override suspend fun getExchanges(): List<Exchange> {
        return remoteDataSource.getExchanges()
    }
}
