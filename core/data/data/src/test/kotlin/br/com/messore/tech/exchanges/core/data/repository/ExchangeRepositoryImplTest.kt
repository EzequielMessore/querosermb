package br.com.messore.tech.exchanges.core.data.repository

import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.testing.ExchangeDataFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.runBlocking

class ExchangeRepositoryImplTest {

    private val remoteDataSource = mockk<ExchangeDataSource.Remote>()
    private val repository = ExchangeRepositoryImpl(remoteDataSource = remoteDataSource)

    @Test
    fun `getExchanges should return exchanges from remote data source`() = runBlocking {
        val expectedExchanges = ExchangeDataFactory.createExchangeList()
        coEvery { remoteDataSource.getExchanges() } returns expectedExchanges

        val result = repository.getExchanges()

        assertEquals(expectedExchanges, result)
        coVerify(exactly = 1) { remoteDataSource.getExchanges() }
    }

    @Test
    fun `getExchanges should return empty list when remote data source returns empty list`() = runBlocking {
        val expected = emptyList<Exchange>()
        coEvery { remoteDataSource.getExchanges() } returns expected

        val result = repository.getExchanges()

        assertEquals(expected, result)
        coVerify(exactly = 1) { remoteDataSource.getExchanges() }
    }

    @Test
    fun `getExchanges should throw exception when remote data source throws exception`() = runBlocking {
        val exception = RuntimeException("Remote data source error")
        coEvery { remoteDataSource.getExchanges() } throws exception

        assertFailsWith<RuntimeException>("Remote data source error") {
            repository.getExchanges()
        }

        coVerify(exactly = 1) { remoteDataSource.getExchanges() }
    }
}