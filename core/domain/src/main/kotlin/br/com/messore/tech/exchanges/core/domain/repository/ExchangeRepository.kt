package br.com.messore.tech.exchanges.core.domain.repository

import br.com.messore.tech.exchanges.core.domain.model.Exchange

interface ExchangeRepository {
    suspend fun getExchanges(): List<Exchange>
}
