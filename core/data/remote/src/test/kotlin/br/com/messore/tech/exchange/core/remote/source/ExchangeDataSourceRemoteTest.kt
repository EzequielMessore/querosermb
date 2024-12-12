package br.com.messore.tech.exchange.core.remote.source

import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchange.core.remote.service.ExchangeService
import br.com.messore.tech.exchange.core.remote.source.factory.ExchangeDataFactory.createExchangeList
import br.com.messore.tech.exchange.core.remote.source.factory.ExchangeDataFactory.createImageList
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest

class ExchangeDataSourceRemoteTest {
    private val exchangeService = mockk<ExchangeService>()
    private val dataSource = ExchangeDataSourceRemote(exchangeService)

    @Test
    fun `test getExchanges returns combined data`() = runTest {
        coEvery { exchangeService.getExchanges() } returns createExchangeList()
        coEvery { exchangeService.getExchangesImages() } returns createImageList()

        val result = dataSource.getExchanges()

        val expected = listOf(
            Exchange(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1DayUsd = 29497214160.58,
                image = "https://www.binance.com/",
            ),
            Exchange(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1DayUsd = 1966886113.43,
                image = "https://www.kraken.com/",
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun `test getExchanges returns empty list when no exchanges or images`() = runTest {
        coEvery { exchangeService.getExchanges() } returns emptyList()
        coEvery { exchangeService.getExchangesImages() } returns emptyList()

        val result = dataSource.getExchanges()

        assertEquals(emptyList<Exchange>(), result)
    }

    @Test
    fun `test getExchanges returns exchanges with empty images when no images`() = runTest {
        coEvery { exchangeService.getExchanges() } returns createExchangeList()
        coEvery { exchangeService.getExchangesImages() } returns emptyList()

        val result = dataSource.getExchanges()

        val expected = listOf(
            Exchange(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1DayUsd = 29497214160.58,
                image = "",
            ),
            Exchange(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1DayUsd = 1966886113.43,
                image = "",
            ),
        )
        assertEquals(expected, result)
    }

    @Test
    fun `test getExchanges returns empty list when no exchanges but images`() = runTest {
        coEvery { exchangeService.getExchanges() } returns emptyList()
        coEvery { exchangeService.getExchangesImages() } returns createImageList()

        val result = dataSource.getExchanges()

        assertEquals(emptyList<Exchange>(), result)
    }

    @Test
    fun `test getExchanges throws exception when getExchanges fails`() = runTest {
        coEvery { exchangeService.getExchanges() } throws RuntimeException("Failed to fetch exchanges")
        coEvery { exchangeService.getExchangesImages() } returns createImageList()

        assertFailsWith<RuntimeException>("Failed to fetch exchanges") {
            dataSource.getExchanges()
        }
    }

    @Test
    fun `test getExchanges returns exchanges with empty images when getExchangesImages fails`() = runTest {
        coEvery { exchangeService.getExchanges() } returns createExchangeList()
        coEvery { exchangeService.getExchangesImages() } throws RuntimeException("Failed to fetch images")

        val result = dataSource.getExchanges()

        val expected = listOf(
            Exchange(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1DayUsd = 29497214160.58,
                image = "",
            ),
            Exchange(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1DayUsd = 1966886113.43,
                image = "",
            ),
        )
        assertEquals(expected, result)
    }
}