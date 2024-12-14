package br.com.messore.tech.exchanges.core.data.repository

import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.testing.ExchangeDataFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ExchangeRepositoryImplTest {

    private val remoteDataSource = mockk<ExchangeDataSource.Remote>()
    private val localDataSource = mockk<ExchangeDataSource.Local>()
    private val repository = ExchangeRepositoryImpl(remoteDataSource, localDataSource)

    @Test
    fun `getExchanges should return exchanges with cached images`() = runTest {
        val exchanges = ExchangeDataFactory.createExchangeList()
        val images = ExchangeDataFactory.createImageList()

        coEvery { localDataSource.hasCacheValid() } returns true
        coEvery { localDataSource.getCachedImages() } returns images
        coEvery { remoteDataSource.getExchanges() } returns exchanges

        val result = repository.getExchanges()

        val expected = exchanges.map { exchange ->
            exchange.copy(image = images.find { it.exchangeId == exchange.exchangeId }?.url.orEmpty())
        }
        assertEquals(expected, result)
        coVerify(exactly = 1) { localDataSource.hasCacheValid() }
        coVerify(exactly = 1) { localDataSource.getCachedImages() }
        coVerify(exactly = 1) { remoteDataSource.getExchanges() }
    }

    @Test
    fun `getExchanges should return exchanges with fetched images when cache is invalid`() = runTest {
        val exchanges = ExchangeDataFactory.createExchangeList()
        val images = ExchangeDataFactory.createImageList()
        coEvery { localDataSource.hasCacheValid() } returns false
        coEvery { remoteDataSource.getImagesExchange() } returns images
        coEvery { localDataSource.saveImagesExchange(images) } returns Unit
        coEvery { remoteDataSource.getExchanges() } returns exchanges

        val result = repository.getExchanges()

        val expected = exchanges.map { exchange ->
            exchange.copy(image = images.find { it.exchangeId == exchange.exchangeId }?.url.orEmpty())
        }
        assertEquals(expected, result)
        coVerify(exactly = 1) { localDataSource.hasCacheValid() }
        coVerify(exactly = 1) { remoteDataSource.getImagesExchange() }
        coVerify(exactly = 1) { localDataSource.saveImagesExchange(images) }
        coVerify(exactly = 1) { remoteDataSource.getExchanges() }
    }

    @Test
    fun `getExchanges should return exchanges without images when getImagesExchange fails`() = runTest {
        val exchanges = ExchangeDataFactory.createExchangeList()
        coEvery { localDataSource.hasCacheValid() } returns false
        coEvery { remoteDataSource.getImagesExchange() } throws RuntimeException("Failed to fetch images")
        coEvery { remoteDataSource.getExchanges() } returns exchanges

        val result = repository.getExchanges()

        val expected = exchanges.map { exchange ->
            exchange.copy(image = "")
        }
        assertEquals(expected = expected, actual = result)
        coVerify(exactly = 1) { localDataSource.hasCacheValid() }
        coVerify(exactly = 1) { remoteDataSource.getImagesExchange() }
        coVerify(exactly = 1) { remoteDataSource.getExchanges() }
    }

    @Test
    fun `getExchanges should throw exception when remote getExchanges fails`() = runTest {
        coEvery { localDataSource.hasCacheValid() } returns true
        coEvery { localDataSource.getCachedImages() } returns ExchangeDataFactory.createImageList()
        coEvery { remoteDataSource.getExchanges() } throws RuntimeException("Failed to fetch exchanges")

        assertFailsWith<RuntimeException>("Failed to fetch exchanges") {
            repository.getExchanges()
        }
        coVerify(exactly = 1) { localDataSource.hasCacheValid() }
        coVerify(exactly = 1) { localDataSource.getCachedImages() }
        coVerify(exactly = 1) { remoteDataSource.getExchanges() }
    }

    @Test
    fun `getExchangeById should return exchange with cached image`() = runTest {
        val exchange = ExchangeDataFactory.createExchangeById("BINANCE")
        val images = ExchangeDataFactory.createImageList()

        coEvery { localDataSource.hasCacheValid() } returns true
        coEvery { localDataSource.getCachedImages() } returns images
        coEvery { remoteDataSource.getExchangeById("BINANCE") } returns exchange

        val result = repository.getExchangeById("BINANCE")

        val expected = exchange.copy(image = images.find { it.exchangeId == exchange.exchangeId }?.url.orEmpty())
        assertEquals(expected, result)
        coVerify(exactly = 1) { localDataSource.hasCacheValid() }
        coVerify(exactly = 1) { localDataSource.getCachedImages() }
        coVerify(exactly = 1) { remoteDataSource.getExchangeById("BINANCE") }
    }

    @Test
    fun `getExchangeById should return exchange without image when getImagesExchange fails`() = runTest {
        val exchange = ExchangeDataFactory.createExchangeById("BINANCE")
        coEvery { localDataSource.hasCacheValid() } returns false
        coEvery { remoteDataSource.getImagesExchange() } throws RuntimeException("Failed to fetch images")
        coEvery { remoteDataSource.getExchangeById("BINANCE") } returns exchange

        val result = repository.getExchangeById("BINANCE")

        val expected = exchange.copy(image = "")
        assertEquals(expected, result)
        coVerify(exactly = 1) { localDataSource.hasCacheValid() }
        coVerify(exactly = 1) { remoteDataSource.getImagesExchange() }
        coVerify(exactly = 1) { remoteDataSource.getExchangeById("BINANCE") }
    }

    @Test
    fun `getExchangeById should throw exception when remote getExchangeById fails`() = runTest {
        coEvery { localDataSource.hasCacheValid() } returns true
        coEvery { localDataSource.getCachedImages() } returns ExchangeDataFactory.createImageList()
        coEvery { remoteDataSource.getExchangeById("BINANCE") } throws RuntimeException("Failed to fetch exchange")

        assertFailsWith<RuntimeException>("Failed to fetch exchange") {
            repository.getExchangeById("BINANCE")
        }
        coVerify(exactly = 1) { localDataSource.hasCacheValid() }
        coVerify(exactly = 1) { localDataSource.getCachedImages() }
        coVerify(exactly = 1) { remoteDataSource.getExchangeById("BINANCE") }
    }
}
