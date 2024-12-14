package br.com.messore.tech.exchages.di

import br.com.messore.tech.exchages.BuildConfig
import br.com.messore.tech.exchanges.core.domain.config.ApiConfig
import br.com.messore.tech.exchanges.view.model.DispatchersProvider
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Single
class AppConfig : ApiConfig {
    override val url = BuildConfig.API_URL
    override val apiKey = BuildConfig.API_KEY
}

@Module
@ComponentScan("br.com.messore.tech.exchanges")
class AppModule {
    @Single
    fun providesDispatcherProvider(): DispatchersProvider = DispatchersProvider()
}
