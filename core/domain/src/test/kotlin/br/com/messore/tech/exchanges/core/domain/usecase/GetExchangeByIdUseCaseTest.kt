package br.com.messore.tech.exchanges.core.domain.usecase

import br.com.messore.tech.exchanges.core.domain.repository.ExchangeRepository
import br.com.messore.tech.exchanges.testing.ExchangeDataFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlinx.coroutines.test.runTest

class GetExchangeByIdUseCaseTest {

    private val repository = mockk<ExchangeRepository>()
    private val useCase = GetExchangeByIdUseCase(repository)

    @Test
    fun `invoke should return exchange from repository`() = runTest {
        val exchangeId = "BINANCE"
        val expected = ExchangeDataFactory.createExchangeById(exchangeId)
        coEvery { repository.getExchangeById(exchangeId) } returns expected

        val result = useCase(exchangeId)

        assertEquals(expected = expected, actual = result)
        coVerify { repository.getExchangeById(exchangeId) }
    }

    @Test
    fun `invoke should throw exception when repository throws exception`() = runTest {
        val exchangeId = "BINANCE"
        val exception = RuntimeException("Repository error")
        coEvery { repository.getExchangeById(exchangeId) } throws exception

        assertFailsWith<RuntimeException>("Repository error") {
            useCase(exchangeId)
        }

        coVerify(exactly = 1) { repository.getExchangeById(exchangeId) }
    }
}
