package br.com.messore.tech.exchanges.details.viewmodel

import androidx.lifecycle.SavedStateHandle
import br.com.messore.tech.exchanges.core.domain.usecase.GetExchangeByIdUseCase
import br.com.messore.tech.exchanges.details.TestCoroutineRule
import br.com.messore.tech.exchanges.details.extensions.mockkToRoute
import br.com.messore.tech.exchanges.navigation.Routes
import br.com.messore.tech.exchanges.testing.ExchangeDataFactory.createExchangeById
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: DetailViewModel
    private val getExchangeByIdUseCase = mockk<GetExchangeByIdUseCase>()
    private val savedStateHandle = mockk<SavedStateHandle>(relaxed = true)

    private val states = mutableListOf<DetailUiState>()

    @BeforeTest
    fun setUp() {
        savedStateHandle.mockkToRoute(Routes.ExchangeDetail("BINANCE"))
        viewModel = DetailViewModel(
            savedHandle = savedStateHandle,
            getExchangeByIdUseCase = getExchangeByIdUseCase,
            dispatchersProvider = testCoroutineRule.providers,
        )
    }

    @Test
    fun `getExchangeById should return exchange`() = runTest {
        val exchangeId = "BINANCE"
        val exchange = createExchangeById(exchangeId)
        coEvery { getExchangeByIdUseCase.invoke(exchangeId) } returns exchange

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        val expectedStates = listOf(
            DetailUiState(),
            DetailUiState(loading = true),
            DetailUiState(loading = false, exchange = exchange),
        )

        assertEquals(expected = expectedStates, actual = states)

        stateJob.cancel()
    }

    @Test
    fun `getExchangeById should handle failure`() = runTest {
        val exchangeId = "BINANCE"
        coEvery { getExchangeByIdUseCase.invoke(exchangeId) } throws RuntimeException("API Error")

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        val expectedStates = listOf(
            DetailUiState(),
            DetailUiState(loading = true),
            DetailUiState(loading = false, hasError = true),
        )

        assertEquals(expected = expectedStates, actual = states)

        stateJob.cancel()
    }
}
