package br.com.messore.tech.exchanges.testing

import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.model.Image

object ExchangeDataFactory {
    fun createExchangeList(): List<Exchange> {
        return listOf(
            Exchange(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1DayUsd = 29497214160.58,
                image = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_64/12706e78b23e41e2afd42eecc28d9bbc.png",
            ),
            Exchange(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1DayUsd = 1966886113.43,
                image = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_64/0fce391a2d564c089735f4e42e0d8d79.png",
            ),
        )
    }

    fun createImageList(): List<Image> {
        return listOf(
            Image(
                url = "https://www.binance.com/",
                exchangeId = "BINANCE",
            ),
            Image(
                url = "https://www.kraken.com/",
                exchangeId = "KRAKEN",
            ),
        )
    }
}
