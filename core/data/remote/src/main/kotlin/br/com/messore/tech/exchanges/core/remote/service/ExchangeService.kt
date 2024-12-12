package br.com.messore.tech.exchanges.core.remote.service

import br.com.messore.tech.exchanges.core.remote.model.Exchange
import br.com.messore.tech.exchanges.core.remote.model.Image

interface ExchangeService {
    suspend fun getExchanges(): List<Exchange>
    suspend fun getExchangesImages(): List<Image>
}
