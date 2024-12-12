package br.com.messore.tech.exchange.core.domain.repository

import br.com.messore.tech.exchange.core.domain.model.Exchange

interface ExchangeRepository {
    suspend fun getExchanges(): List<Exchange>
}
