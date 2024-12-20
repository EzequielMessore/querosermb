package br.com.messore.tech.exchanges.core.data.di

import br.com.messore.tech.exchanges.core.data.repository.ExchangeRepositoryImpl
import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.repository.ExchangeRepository
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataModule {
    @Single
    fun providesExchangeRepository(
        localSource: ExchangeDataSource.Local,
        remoteSource: ExchangeDataSource.Remote,
    ): ExchangeRepository = ExchangeRepositoryImpl(
        localDataSource = localSource,
        remoteDataSource = remoteSource,
    )
}
