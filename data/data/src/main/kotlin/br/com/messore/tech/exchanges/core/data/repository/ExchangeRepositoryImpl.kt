package br.com.messore.tech.exchanges.core.data.repository

import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.model.Image
import br.com.messore.tech.exchanges.core.domain.repository.ExchangeRepository

class ExchangeRepositoryImpl(
    private val remoteDataSource: ExchangeDataSource.Remote,
    private val localDataSource: ExchangeDataSource.Local,
) : ExchangeRepository {

    override suspend fun getExchanges(): List<Exchange> {
        val cachedImages = getCachedImages()
        return remoteDataSource.getExchanges().map { exchange ->
            exchange.copy(image = cachedImages.find { it.exchangeId == exchange.exchangeId }?.url.orEmpty())
        }
    }

    override suspend fun getExchangeById(id: String): Exchange {
        val cachedImages = getCachedImages()
        val exchange = remoteDataSource.getExchangeById(id)
        val imageUrl = cachedImages.find { it.exchangeId == exchange.exchangeId }?.url.orEmpty()
        return exchange.copy(image = imageUrl)
    }

    private suspend fun getCachedImages(): List<Image> {
        return if (localDataSource.hasCacheValid()) {
            localDataSource.getCachedImages()
        } else {
            runCatching {
                remoteDataSource.getImagesExchange().also {
                    localDataSource.saveImagesExchange(it)
                }
            }.getOrNull().orEmpty()
        }
    }
}