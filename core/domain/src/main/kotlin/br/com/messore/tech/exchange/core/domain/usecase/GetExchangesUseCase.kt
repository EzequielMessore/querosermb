package br.com.messore.tech.exchange.core.domain.usecase

import br.com.messore.tech.exchange.core.domain.repository.ExchangeRepository

class GetExchangesUseCase(
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke() = repository.getExchanges()
}
