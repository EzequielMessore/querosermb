package br.com.messore.tech.exchanges.core.domain.usecase

import br.com.messore.tech.exchanges.core.domain.repository.ExchangeRepository

class GetExchangeByIdUseCase(
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke(id: String) = repository.getExchangeById(id)
}
