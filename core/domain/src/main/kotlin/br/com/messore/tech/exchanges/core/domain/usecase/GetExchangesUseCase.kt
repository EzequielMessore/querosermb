package br.com.messore.tech.exchanges.core.domain.usecase

import br.com.messore.tech.exchanges.core.domain.repository.ExchangeRepository

class GetExchangesUseCase(
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke() = repository.getExchanges()
}
