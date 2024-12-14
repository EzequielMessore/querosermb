package br.com.messore.tech.exchanges.features.exchanges.viewmodel

import br.com.messore.tech.exchanges.core.domain.usecase.GetExchangesUseCase
import br.com.messore.tech.exchanges.testing.ExchangeDataFactory.createExchangeList
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
class ExchangeViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: ExchangeViewModel
    private val getExchangesUseCase = mockk<GetExchangesUseCase>()

    private val states = mutableListOf<ExchangeUiState>()

    @BeforeTest
    fun setUp() {
        viewModel = ExchangeViewModel(
            getExchangeUseCase = getExchangesUseCase,
            dispatchersProvider = testCoroutineRule.providers,
        )
    }

    @Test
    fun `getExchanges should return list of exchanges`() = runTest {
        val exchanges = createExchangeList()
        coEvery { getExchangesUseCase.invoke() } returns exchanges

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        val expectedStates = listOf(
            ExchangeUiState(),
            ExchangeUiState(loading = true),
            ExchangeUiState(loading = false, exchanges = exchanges),
        )

        assertEquals(expectedStates, states)

        stateJob.cancel()
    }

    @Test
    fun `getExchanges should handle failure`() = runTest {
        coEvery { getExchangesUseCase.invoke() } throws RuntimeException("API Error")

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        val expectedStates = listOf(
            ExchangeUiState(),
            ExchangeUiState(loading = true),
            ExchangeUiState(loading = false, hasError = true),
        )

        assertEquals(expectedStates, states)

        stateJob.cancel()
    }

    @Test
    fun `onSearchTermChange should filter exchanges ignoring case sensitivity`() = runTest {
        val exchanges = createExchangeList()
        coEvery { getExchangesUseCase.invoke() } returns exchanges

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        viewModel.onAction(ExchangeUiAction.OnSearchTermChange("BINANCE"))
        advanceUntilIdle()

        val filteredExchanges = exchanges.filter { it.name.equals("Binance", ignoreCase = true) }
        val expectedStates = listOf(
            ExchangeUiState(),
            ExchangeUiState(loading = true),
            ExchangeUiState(loading = false, exchanges = exchanges),
            ExchangeUiState(loading = false, exchanges = exchanges, searchTerm = "BINANCE", filteredExchanges = filteredExchanges),
        )

        assertEquals(expectedStates, states)

        stateJob.cancel()
    }

    @Test
    fun `onAction SelectExchange should not change state`() = runTest {
        val exchanges = createExchangeList()
        coEvery { getExchangesUseCase.invoke() } returns exchanges

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        viewModel.onAction(ExchangeUiAction.SelectExchange(exchange = exchanges.first()))
        advanceUntilIdle()

        val expectedStates = listOf(
            ExchangeUiState(),
            ExchangeUiState(loading = true),
            ExchangeUiState(loading = false, exchanges = exchanges),
        )

        assertEquals(expectedStates, states)

        stateJob.cancel()
    }

    @Test
    fun `onSearchTermChange should handle multiple updates`() = runTest {
        val exchanges = createExchangeList()
        coEvery { getExchangesUseCase.invoke() } returns exchanges

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        val initialStates = listOf(
            ExchangeUiState(),
            ExchangeUiState(loading = true),
            ExchangeUiState(loading = false, exchanges = exchanges),
        )

        assertEquals(expected = initialStates, actual = states)

        viewModel.onAction(ExchangeUiAction.OnSearchTermChange("binance"))
        advanceUntilIdle()

        val binanceFilteredState = ExchangeUiState(
            loading = false,
            exchanges = exchanges,
            searchTerm = "binance",
            filteredExchanges = exchanges.filter { it.name.contains("binance", ignoreCase = true) },
        )

        val expectedStatesAfterBinance = initialStates + binanceFilteredState

        assertEquals(expected = expectedStatesAfterBinance, actual = states)

        viewModel.onAction(ExchangeUiAction.OnSearchTermChange("kraken"))
        advanceUntilIdle()

        val krakenFilteredState = ExchangeUiState(
            loading = false,
            exchanges = exchanges,
            searchTerm = "kraken",
            filteredExchanges = exchanges.filter { it.name.contains("kraken", ignoreCase = true) },
        )

        val expectedStatesAfterKraken = expectedStatesAfterBinance + krakenFilteredState

        assertEquals(expected = expectedStatesAfterKraken, actual = states)

        stateJob.cancel()
    }

    @Test
    fun `onSearchTermChange with empty term should clear filteredExchanges`() = runTest {
        val exchanges = createExchangeList()
        coEvery { getExchangesUseCase.invoke() } returns exchanges

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        val initialStates = listOf(
            ExchangeUiState(),
            ExchangeUiState(loading = true),
            ExchangeUiState(loading = false, exchanges = exchanges),
        )

        assertEquals(expected = initialStates, actual = states)

        viewModel.onAction(ExchangeUiAction.OnSearchTermChange("binance"))
        advanceUntilIdle()

        val binanceFilteredState = ExchangeUiState(
            loading = false,
            exchanges = exchanges,
            searchTerm = "binance",
            filteredExchanges = exchanges.filter { it.name.contains("binance", ignoreCase = true) },
        )

        val expectedStatesAfterBinance = initialStates + binanceFilteredState

        assertEquals(expected = expectedStatesAfterBinance, actual = states)

        viewModel.onAction(ExchangeUiAction.OnSearchTermChange(""))
        advanceUntilIdle()

        val emptyFilteredState = ExchangeUiState(
            loading = false,
            exchanges = exchanges,
            searchTerm = "",
            filteredExchanges = emptyList(),
        )

        val expectedStatesAfterEmpty = expectedStatesAfterBinance + emptyFilteredState

        assertEquals(expected = expectedStatesAfterEmpty, actual = states)

        stateJob.cancel()
    }

    @Test
    fun `retry should reload exchanges`() = runTest {
        val initialState = ExchangeUiState()
        val exchanges = createExchangeList()
        coEvery { getExchangesUseCase.invoke() } throws RuntimeException("API Error") andThen exchanges

        val stateJob = launch { viewModel.state.toList(states) }
        advanceUntilIdle()

        // Initial failure
        val expectedStates = listOf(
            initialState,
            initialState.copy(loading = true),
            initialState.copy(loading = false, hasError = true),
        )

        assertEquals(expected = expectedStates, actual = states)

        // Retry action
        viewModel.onAction(ExchangeUiAction.Retry)
        advanceUntilIdle()

        // Successful retry
        val expectedStatesAfterRetry = expectedStates + listOf(
            initialState.copy(hasError = false),
            initialState.copy(loading = true),
            initialState.copy(loading = false, exchanges = exchanges),
        )

        assertEquals(expected = expectedStatesAfterRetry, actual = states)

        stateJob.cancel()
    }
}
