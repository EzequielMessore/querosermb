package br.com.messore.tech.exchanges.core.data.source

import br.com.messore.tech.exchanges.core.domain.model.Exchange

interface ExchangeDataSource {
    interface Remote : ExchangeDataSource {
        suspend fun getExchanges(): List<Exchange>
    }
}
