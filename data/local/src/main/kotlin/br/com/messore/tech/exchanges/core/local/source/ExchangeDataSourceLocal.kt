package br.com.messore.tech.exchanges.core.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.messore.tech.exchanges.core.data.source.ExchangeDataSource
import br.com.messore.tech.exchanges.core.domain.model.Image
import java.time.Duration
import java.time.Instant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ExchangeDataSourceLocal(
    private val dataStore: DataStore<Preferences>,
) : ExchangeDataSource.Local {
    private val imageExchanges = stringPreferencesKey("image_exchange_json")
    private val cacheTimeKey = longPreferencesKey("cache_time")

    override suspend fun saveImagesExchange(images: List<Image>) {
        val currentTime = Instant.now().toEpochMilli()
        dataStore.edit { preferences ->
            preferences[imageExchanges] = Json.encodeToString(images)
            preferences[cacheTimeKey] = currentTime
        }
    }

    override suspend fun hasCacheValid(): Boolean {
        val cacheTime = dataStore.data.map { preferences ->
            preferences[cacheTimeKey] ?: 0L
        }.first()
        val currentTime = Instant.now().toEpochMilli()
        val diffInMinutes = Duration.between(Instant.ofEpochMilli(cacheTime), Instant.ofEpochMilli(currentTime)).toMinutes()
        return diffInMinutes <= 60
    }

    override suspend fun getCachedImages(): List<Image> {
        val json = dataStore.data.map { preferences ->
            preferences[imageExchanges].orEmpty()
        }.first()
        return if (json.isNotEmpty()) {
            Json.decodeFromString(json)
        } else {
            emptyList()
        }
    }
}