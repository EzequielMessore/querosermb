package br.com.messore.tech.exchanges.core.domain.usecase

import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.repository.ExchangeRepository
import br.com.messore.tech.exchanges.testing.ExchangeDataFactory
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetExchangesUseCaseTest {

    private val repository = mockk<ExchangeRepository>()
    private val useCase = GetExchangesUseCase(repository)

    @Test
    fun `invoke should return exchanges from repository`() = runTest {
        val expected = ExchangeDataFactory.createExchangeList()
        coEvery { repository.getExchanges() } returns expected

        val result = useCase()

        assertEquals(expected = expected, actual = result)
        coVerify { repository.getExchanges() }
    }

    @Test
    fun `invoke should return empty list when repository returns empty list`() = runTest {
        val expected = emptyList<Exchange>()
        coEvery { repository.getExchanges() } returns expected

        val result = useCase()

        assertEquals(expected = expected, actual = result)
        coVerify(exactly = 1) { repository.getExchanges() }
    }

    @Test
    fun `invoke should throw exception when repository throws exception`() = runTest {
        val exception = RuntimeException("Repository error")
        coEvery { repository.getExchanges() } throws exception

        assertFailsWith<RuntimeException>("Repository error") {
            useCase()
        }

        coVerify(exactly = 1) { repository.getExchanges() }
    }
}
