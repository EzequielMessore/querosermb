package br.com.messore.tech.exchange.core.remote.source.factory

import br.com.messore.tech.exchange.core.remote.model.Exchange as ExchangeRemote
import br.com.messore.tech.exchange.core.remote.model.Image as ImageRemote

object ExchangeDataFactory {
    fun createExchangeList(): List<ExchangeRemote> {
        return listOf(
            ExchangeRemote(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1DayUsd = 29497214160.58,
            ),
            ExchangeRemote(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1DayUsd = 1966886113.43,
            ),
        )
    }

    fun createImageList(): List<ImageRemote> {
        return listOf(
            ImageRemote(
                url = "https://www.binance.com/",
                exchangeId = "BINANCE",
            ),
            ImageRemote(
                url = "https://www.kraken.com/",
                exchangeId = "KRAKEN",
            ),
        )
    }
}