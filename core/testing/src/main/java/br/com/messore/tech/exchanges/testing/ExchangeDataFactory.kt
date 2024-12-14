package br.com.messore.tech.exchanges.testing

import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.model.Image

object ExchangeDataFactory {
    private const val BASE_IMAGE_URL = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_64/"

    fun createExchangeList(): List<Exchange> {
        return listOf(
            Exchange(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1DayUsd = 128855794.37,
                volume1HourUsd = 29497214160.58,
                volume1MonthUsd = 1234297742912.71,
                image = "${BASE_IMAGE_URL}12706e78b23e41e2afd42eecc28d9bbc.png",
            ),
            Exchange(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1DayUsd = 7611640.34,
                volume1HourUsd = 1966886113.43,
                volume1MonthUsd = 75269293477.50,
                image = "${BASE_IMAGE_URL}0fce391a2d564c089735f4e42e0d8d79.png",
            ),
        )
    }

    fun createExchangeById(exchangeId: String) = createExchangeList().find { it.exchangeId == exchangeId }
        ?: throw IllegalArgumentException("Exchange not found")

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
