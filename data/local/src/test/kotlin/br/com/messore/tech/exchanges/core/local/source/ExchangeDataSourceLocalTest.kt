package br.com.messore.tech.exchanges.core.local.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.messore.tech.exchanges.testing.ExchangeDataFactory.createImageList
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class ExchangeDataSourceLocalTest {

    private val dataStore = mockk<DataStore<Preferences>>()
    private val dataSource = ExchangeDataSourceLocal(dataStore)

    private fun mockDataStoreData(cacheTime: Long? = null, json: String? = null) {
        coEvery { dataStore.data } returns flowOf(
            mockk {
                every { get(longPreferencesKey("cache_time")) } returns cacheTime
                every { get(stringPreferencesKey("image_exchange_json")) } returns json
            },
        )
    }

    @Test
    fun `hasCacheValid should return true when cache is valid`() = runTest {
        val currentTime = System.currentTimeMillis()
        val cacheTime = currentTime - 30.minutes.inWholeMilliseconds
        mockDataStoreData(cacheTime = cacheTime)

        val result = dataSource.hasCacheValid()

        assertTrue(result)
    }

    @Test
    fun `hasCacheValid should return false when cache is invalid`() = runTest {
        val currentTime = System.currentTimeMillis()
        val cacheTime = currentTime - 2.hours.inWholeMilliseconds
        mockDataStoreData(cacheTime = cacheTime)

        val result = dataSource.hasCacheValid()

        assertTrue(!result)
    }

    @Test
    fun `getCachedImages should return cached images`() = runTest {
        val images = createImageList()
        val json = Json.encodeToString(images)
        mockDataStoreData(json = json)

        val result = dataSource.getCachedImages()

        assertEquals(images, result)
    }

    @Test
    fun `getCachedImages should return empty list when no cached images`() = runTest {
        mockDataStoreData()

        val result = dataSource.getCachedImages()

        assertTrue(result.isEmpty())
    }
}
