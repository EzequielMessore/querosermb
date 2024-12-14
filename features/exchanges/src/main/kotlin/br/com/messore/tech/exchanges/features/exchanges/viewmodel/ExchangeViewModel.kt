package br.com.messore.tech.exchanges.features.exchanges.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.usecase.GetExchangesUseCase
import br.com.messore.tech.exchanges.view.model.DispatchersProvider
import br.com.messore.tech.exchanges.view.model.execute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ExchangeViewModel(
    private val getExchangeUseCase: GetExchangesUseCase,
    private val dispatchersProvider: DispatchersProvider,
) : ViewModel() {
    private val _state = MutableStateFlow(ExchangeUiState())
    val state = _state.asStateFlow()
        .onStart { getExchanges() }
        .stateIn(
            scope = viewModelScope,
            initialValue = ExchangeUiState(),
            started = SharingStarted.WhileSubscribed(5_000L),
        )

    private fun getExchanges() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        execute(
            dispatcher = dispatchersProvider.io,
            execute = getExchangeUseCase::invoke,
            onSuccess = { exchanges ->
                _state.update { it.copy(loading = false, exchanges = exchanges) }
            },
            onFailure = {
                _state.update {
                    it.copy(
                        loading = false,
                        hasError = true,
                    )
                }
            },
        )
    }

    fun onAction(action: ExchangeUiAction) {
        when (action) {
            ExchangeUiAction.Retry -> {
                _state.update { it.copy(hasError = false) }
                getExchanges()
            }

            is ExchangeUiAction.SelectExchange -> Unit
            is ExchangeUiAction.OnSearchTermChange -> {
                when {
                    action.searchTerm.isBlank() -> _state.update {
                        it.copy(searchTerm = action.searchTerm, filteredExchanges = emptyList())
                    }

                    else -> {
                        val exchanges = _state.value.exchanges.filter {
                            it.name.contains(action.searchTerm, ignoreCase = true)
                        }
                        _state.update { it.copy(searchTerm = action.searchTerm, filteredExchanges = exchanges) }
                    }
                }
            }
        }
    }
}

data class ExchangeUiState(
    val searchTerm: String = "",
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val exchanges: List<Exchange> = emptyList(),
    val filteredExchanges: List<Exchange> = emptyList(),
)

sealed interface ExchangeUiAction {
    data object Retry : ExchangeUiAction
    data class SelectExchange(val exchange: Exchange) : ExchangeUiAction
    data class OnSearchTermChange(val searchTerm: String) : ExchangeUiAction
}