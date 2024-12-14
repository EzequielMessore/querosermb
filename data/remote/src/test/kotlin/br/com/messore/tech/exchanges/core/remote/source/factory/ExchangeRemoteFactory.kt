package br.com.messore.tech.exchanges.core.remote.source.factory

import br.com.messore.tech.exchanges.core.remote.model.Exchange as ExchangeRemote
import br.com.messore.tech.exchanges.core.remote.model.Image as ImageRemote

object ExchangeRemoteFactory {
    fun createExchangeList(): List<ExchangeRemote> {
        return listOf(
            ExchangeRemote(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1HourUsd = 128855794.37,
                volume1DayUsd = 29497214160.58,
                volume1MonthUsd = 1234297742912.71,
            ),
            ExchangeRemote(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1HourUsd = 7611640.34,
                volume1DayUsd = 1966886113.43,
                volume1MonthUsd = 75269293477.50,
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
