package br.com.messore.tech.exchanges.core.data.source

import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.model.Image

interface ExchangeDataSource {
    interface Remote : ExchangeDataSource {
        suspend fun getExchanges(): List<Exchange>
        suspend fun getImagesExchange(): List<Image>
        suspend fun getExchangeById(id: String): Exchange
    }

    interface Local : ExchangeDataSource {
        suspend fun hasCacheValid(): Boolean
        suspend fun getCachedImages(): List<Image>
        suspend fun saveImagesExchange(images: List<Image>)
    }
}
