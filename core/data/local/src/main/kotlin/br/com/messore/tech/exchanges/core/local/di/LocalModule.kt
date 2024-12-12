package br.com.messore.tech.exchanges.core.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import br.com.messore.tech.exchanges.core.local.source.ExchangeDataSourceLocal
import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

private const val PREFERENCES_NAME = "app_prefs"

@Module
class LocalModule {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    @Single
    fun provideDataStore(context: Context): DataStore<Preferences> = context.dataStore

    @Single
    fun provideExchangeDataSourceLocal(
        dataStore: DataStore<Preferences>,
    ): ExchangeDataSource.Local = ExchangeDataSourceLocal(dataStore)

}