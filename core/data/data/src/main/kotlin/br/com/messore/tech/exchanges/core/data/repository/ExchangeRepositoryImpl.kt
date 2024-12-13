package br.com.messore.tech.exchanges.core.data.repository

import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.repository.ExchangeRepository

class ExchangeRepositoryImpl(
    private val remoteDataSource: ExchangeDataSource.Remote,
    private val localDataSource: ExchangeDataSource.Local,
) : ExchangeRepository {
    override suspend fun getExchanges(): List<Exchange> {
        val cachedImages = if (localDataSource.hasCacheValid()) {
            localDataSource.getCachedImages()
        } else {
            runCatching {
                remoteDataSource.getImagesExchange().also {
                    localDataSource.saveImagesExchange(it)
                }
            }.getOrNull().orEmpty()
        }

        return remoteDataSource.getExchanges().map { exchange ->
            exchange.copy(image = cachedImages.find { it.exchangeId == exchange.exchangeId }?.url.orEmpty())
        }
    }
}
