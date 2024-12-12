package br.com.messore.tech.exchange.core.remote.service

import br.com.messore.tech.exchange.core.remote.model.Exchange
import br.com.messore.tech.exchange.core.remote.model.Image

interface ExchangeService {
    suspend fun getExchanges(): List<Exchange>
    suspend fun getExchangesImages(): List<Image>
}
