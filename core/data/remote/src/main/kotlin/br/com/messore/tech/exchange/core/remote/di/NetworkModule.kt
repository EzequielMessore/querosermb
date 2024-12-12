package br.com.messore.tech.exchange.core.remote.di

import br.com.messore.tech.exchange.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.config.ApiConfig
import br.com.messore.tech.exchange.core.remote.api.ExchangeApi
import br.com.messore.tech.exchange.core.remote.service.ExchangeService
import br.com.messore.tech.exchange.core.remote.source.ExchangeDataSourceRemote
import io.ktor.client.HttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {
    @Single
    fun provideHttpClient(
        apiConfig: ApiConfig,
    ) = createHttpClient(apiConfig)

    @Single
    fun provideExchangeApi(
        httpClient: HttpClient,
    ): ExchangeService = ExchangeApi(httpClient)

    @Single
    fun providesExchangeDataSourceRemote(
        exchangeService: ExchangeService,
    ): ExchangeDataSource.Remote = ExchangeDataSourceRemote(exchangeService)
}
