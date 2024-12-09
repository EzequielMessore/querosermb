package br.com.messore.tech.exchange.core.data.di

import br.com.messore.tech.exchange.core.data.createHttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class CoreDataModule {
    @Single
    fun provideHttpClient() = createHttpClient()
}
