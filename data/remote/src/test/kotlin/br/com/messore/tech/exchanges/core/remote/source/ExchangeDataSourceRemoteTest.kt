package br.com.messore.tech.exchanges.core.remote.source

import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.model.Image
import br.com.messore.tech.exchanges.core.remote.service.ExchangeService
import br.com.messore.tech.exchanges.core.remote.source.factory.ExchangeRemoteFactory.createExchangeList
import br.com.messore.tech.exchanges.core.remote.source.factory.ExchangeRemoteFactory.createImageList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExchangeDataSourceRemoteTest {
    private val exchangeService = mockk<ExchangeService>()
    private val dataSource = ExchangeDataSourceRemote(exchangeService)

    @Test
    fun `test getExchanges returns exchanges successfully`() = runTest {
        coEvery { exchangeService.getExchanges() } returns createExchangeList()

        val result = dataSource.getExchanges()

        val expected = listOf(
            Exchange(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1HourUsd = 128855794.37,
                volume1DayUsd = 29497214160.58,
                volume1MonthUsd = 1234297742912.71,
                image = null,
            ),
            Exchange(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1HourUsd = 7611640.34,
                volume1DayUsd = 1966886113.43,
                volume1MonthUsd = 75269293477.50,
                image = null,
            ),
        )
        assertEquals(expected, result)
        coVerify(exactly = 1) { exchangeService.getExchanges() }
    }

    @Test
    fun `test getExchanges throws exception`() = runTest {
        coEvery { exchangeService.getExchanges() } throws RuntimeException("Failed to fetch exchanges")

        assertFailsWith<RuntimeException>("Failed to fetch exchanges") {
            dataSource.getExchanges()
        }
        coVerify(exactly = 1) { exchangeService.getExchanges() }
    }

    @Test
    fun `test getExchangesImages returns images successfully`() = runTest {
        coEvery { exchangeService.getExchangesImages() } returns createImageList()

        val result = dataSource.getImagesExchange()

        val expected = listOf(
            Image(
                url = "https://www.binance.com/",
                exchangeId = "BINANCE",
            ),
            Image(
                url = "https://www.kraken.com/",
                exchangeId = "KRAKEN",
            ),
        )
        assertEquals(expected, result)
        coVerify(exactly = 1) { exchangeService.getExchangesImages() }
    }

    @Test
    fun `test getExchangesImages throws exception`() = runTest {
        coEvery { exchangeService.getExchangesImages() } throws RuntimeException("Failed to fetch images")

        assertFailsWith<RuntimeException>("Failed to fetch images") {
            dataSource.getImagesExchange()
        }
        coVerify(exactly = 1) { exchangeService.getExchangesImages() }
    }

    @Test
    fun `test getExchangeById returns exchange successfully`() = runTest {
        coEvery { exchangeService.getExchangeById("BINANCE") } returns createExchangeList().first()

        val result = dataSource.getExchangeById("BINANCE")

        val expected = Exchange(
            exchangeId = "BINANCE",
            website = "https://www.binance.com/",
            name = "Binance",
            volume1HourUsd = 128855794.37,
            volume1DayUsd = 29497214160.58,
            volume1MonthUsd = 1234297742912.71,
            image = null,
        )
        assertEquals(expected, result)
        coVerify(exactly = 1) { exchangeService.getExchangeById("BINANCE") }
    }

    @Test
    fun `test getExchangeById throws exception`() = runTest {
        coEvery { exchangeService.getExchangeById("BINANCE") } throws RuntimeException("Failed to fetch exchange")

        assertFailsWith<RuntimeException>("Failed to fetch exchange") {
            dataSource.getExchangeById("BINANCE")
        }
        coVerify(exactly = 1) { exchangeService.getExchangeById("BINANCE") }
    }
}
