package br.com.messore.tech.exchanges.features.exchanges

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.features.exchanges.ui.ExchangeScreen
import br.com.messore.tech.exchanges.features.exchanges.viewmodel.ExchangeUiAction
import br.com.messore.tech.exchanges.features.exchanges.viewmodel.ExchangeUiState
import org.junit.Rule
import org.junit.Test

class ExchangeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Teste de Exibição de Carregamento (Loading)
    @Test
    fun shouldDisplayLoadingWhenDataIsBeingFetched() {
        val exchanges = createExchangeList()
        val state = ExchangeUiState(
            loading = true,
            exchanges = exchanges,
            filteredExchanges = exchanges
        )

        composeTestRule.setContent {
            ExchangeScreen(
                state = state,
                onAction = {}
            )
        }

        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    // 2. Teste de Exibição de Resultados após Carregamento
    @Test
    fun shouldDisplayExchangesAfterLoading() {
        val exchanges = createExchangeList()
        val state = ExchangeUiState(
            loading = false,
            exchanges = exchanges,
        )

        composeTestRule.setContent {
            ExchangeScreen(
                state = state,
                onAction = {}
            )
        }

        composeTestRule.onNodeWithText("Binance").assertIsDisplayed()
        composeTestRule.onNodeWithText("Kraken").assertIsDisplayed()
    }

    // 3. Teste de Filtragem de Exchanges pelo Nome
     @Test
    fun shouldFilterExchangesBasedOnSearchTerm() {
        val exchanges = createExchangeList()
        var searchTerm = "Binance"
        val state = ExchangeUiState(
            loading = false,
            exchanges = exchanges,
            filteredExchanges = exchanges.filter { it.name.contains(searchTerm, ignoreCase = true) }
        )

        composeTestRule.setContent {
            ExchangeScreen(
                state = state,
                onAction = { action ->
                    if (action is ExchangeUiAction.OnSearchTermChange) {
                        searchTerm = action.searchTerm
                    }
                }
            )
        }

        composeTestRule.onNodeWithText("Binance").assertIsDisplayed()
        composeTestRule.onNodeWithText("Kraken").assertDoesNotExist()
    }

    // 4. Teste de Ação de Limpeza da Busca (Search Clear)
    @Test
    fun shouldClearSearchTermWhenClearIconIsClicked() {
        val exchanges = createExchangeList()
        var searchTerm = "Binance"
        val state = ExchangeUiState(
            loading = false,
            exchanges = exchanges,
            filteredExchanges = exchanges.filter { it.name.contains(searchTerm, ignoreCase = true) },
            searchTerm = searchTerm
        )

        composeTestRule.setContent {
            ExchangeScreen(
                state = state,
                onAction = { action ->
                    if (action is ExchangeUiAction.OnSearchTermChange) {
                        searchTerm = action.searchTerm
                    }
                }
            )
        }

        composeTestRule.onNodeWithContentDescription("Close Icon").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Close Icon").performClick()
        assert(searchTerm.isEmpty()) { "O termo de busca não foi limpo!" }
    }


    private fun createExchangeList(): List<Exchange> {
        return listOf(
            Exchange(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1DayUsd = 29497214160.58,
            ),
            Exchange(
                exchangeId = "KRAKEN",
                website = "https://www.kraken.com/",
                name = "Kraken",
                volume1DayUsd = 1966886113.43,
            ),
        )
    }
}
