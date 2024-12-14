package br.com.messore.tech.exchanges.navigation

import kotlinx.serialization.Serializable

object Routes {
    @Serializable
    object Exchanges

    @Serializable
    data class ExchangeDetail(
        val exchangeId: String
    )
}
