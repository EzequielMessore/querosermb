package br.com.messore.tech.exchanges.details.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.domain.usecase.GetExchangeByIdUseCase
import br.com.messore.tech.exchanges.navigation.Routes
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
class DetailViewModel(
    savedHandle: SavedStateHandle,
    private val dispatchersProvider: DispatchersProvider,
    private val getExchangeByIdUseCase: GetExchangeByIdUseCase,
) : ViewModel() {
    private val exchangeId = savedHandle.toRoute<Routes.ExchangeDetail>().exchangeId

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()
        .onStart { getExchangeById() }
        .stateIn(
            scope = viewModelScope,
            initialValue = _state.value,
            started = SharingStarted.WhileSubscribed(5_000L),
        )

    private fun getExchangeById() = viewModelScope.launch {
        _state.update { it.copy(loading = true) }
        execute(
            dispatcher = dispatchersProvider.io,
            execute = { getExchangeByIdUseCase(exchangeId) },
            onSuccess = { exchange ->
                _state.update { it.copy(loading = false, exchange = exchange) }
            },
            onFailure = {
                _state.update { it.copy(loading = false, hasError = true) }
            },
        )
    }
}

data class DetailUiState(
    val loading: Boolean = false,
    val hasError: Boolean = false,
    val exchange: Exchange? = null,
)
