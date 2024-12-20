package br.com.messore.tech.exchanges.core.remote.api

import br.com.messore.tech.exchanges.core.remote.util.HttpClientMock
import br.com.messore.tech.exchanges.core.remote.util.loadJson
import br.com.messore.tech.exchanges.core.remote.util.respondSuccess
import io.ktor.client.engine.mock.MockEngine
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ExchangeApiTest {

    @Test
    fun `test getExchanges returns list of exchanges`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals(request.url.encodedPath, "/exchanges")
            assertEquals(request.headers["X-CoinAPI-Key"], "API_KEY")

            respondSuccess(loadJson("exchanges.json").orEmpty())
        }

        ExchangeApi(HttpClientMock(mockEngine).httpClient).getExchanges().also {
            assertNotNull(it)
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun `test getExchangesImages returns list of images`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals(request.url.encodedPath, "/exchanges/icons/64")
            assertEquals(request.headers["X-CoinAPI-Key"], "API_KEY")

            respondSuccess(loadJson("exchanges_icons.json").orEmpty())
        }

        ExchangeApi(HttpClientMock(mockEngine).httpClient).getExchangesImages().also {
            assertNotNull(it)
            assertTrue(it.isNotEmpty())
        }
    }

    @Test
    fun `test getExchangeById returns exchange with image`() = runTest {
        val mockEngine = MockEngine { request ->
            assertEquals(request.url.encodedPath, "/exchanges")
            assertEquals(request.url.parameters["filter_exchange_id"], "BINANCE")
            assertEquals(request.headers["X-CoinAPI-Key"], "API_KEY")

            respondSuccess(loadJson("exchange_by_id.json").orEmpty())
        }

        ExchangeApi(HttpClientMock(mockEngine).httpClient).getExchangeById("BINANCE").also {
            assertNotNull(it)
            assertEquals(it.exchangeId, "BINANCE")
        }
    }
}
