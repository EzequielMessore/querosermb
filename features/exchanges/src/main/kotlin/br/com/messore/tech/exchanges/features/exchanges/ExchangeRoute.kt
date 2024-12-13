package br.com.messore.tech.exchanges.features.exchanges

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.features.exchanges.ui.ExchangeScreen
import br.com.messore.tech.exchanges.features.exchanges.viewmodel.ExchangeUiAction
import br.com.messore.tech.exchanges.features.exchanges.viewmodel.ExchangeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ExchangeRoute(
    onExchangeSelect: (Exchange) -> Unit = {},
    viewModel: ExchangeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ExchangeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ExchangeUiAction.SelectExchange -> {
                    onExchangeSelect(action.exchange)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}
