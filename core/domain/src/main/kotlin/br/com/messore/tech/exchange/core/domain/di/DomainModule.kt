package br.com.messore.tech.exchange.core.domain.di

import br.com.messore.tech.exchange.core.domain.repository.ExchangeRepository
import br.com.messore.tech.exchange.core.domain.usecase.GetExchangesUseCase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DomainModule {
    @Single
    fun provideGetExchangesUseCase(
        repository: ExchangeRepository,
    ) = GetExchangesUseCase(repository)
}
