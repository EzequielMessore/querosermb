package br.com.messore.tech.exchanges.core.remote.mapper

import kotlin.test.Test
import kotlin.test.assertEquals
import br.com.messore.tech.exchanges.core.domain.model.Exchange as ExchangeDomain
import br.com.messore.tech.exchanges.core.domain.model.Image as ImageDomain
import br.com.messore.tech.exchanges.core.remote.model.Exchange as ExchangeRemote
import br.com.messore.tech.exchanges.core.remote.model.Image as ImageRemote

class ExchangeMapperTest {

    @Test
    fun `test ExchangeRemote to ExchangeDomain mapping`() {
        val exchangeRemote = ExchangeRemote(
            name = "Binance",
            website = "https://www.binance.com/",
            exchangeId = "BINANCE",
            volume1DayUsd = 29497214160.58,
            volume1HourUsd = 128855794.37,
            volume1MonthUsd = 1234297742912.71,
        )

        val exchangeDomain = exchangeRemote.toDomain()

        val expected = ExchangeDomain(
            name = "Binance",
            website = "https://www.binance.com/",
            exchangeId = "BINANCE",
            volume1DayUsd = 29497214160.58,
            volume1HourUsd = 128855794.37,
            volume1MonthUsd = 1234297742912.71,
        )

        assertEquals(expected, exchangeDomain)
    }

    @Test
    fun `test ImageRemote to ImageDomain mapping`() {
        val imageRemote = ImageRemote(
            url = "https://www.binance.com/",
            exchangeId = "BINANCE",
        )

        val imageDomain = imageRemote.toDomain()

        val expected = ImageDomain(
            url = "https://www.binance.com/",
            exchangeId = "BINANCE",
        )

        assertEquals(expected, imageDomain)
    }
}
