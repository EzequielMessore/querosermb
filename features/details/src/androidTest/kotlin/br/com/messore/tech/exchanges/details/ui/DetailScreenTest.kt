package br.com.messore.tech.exchanges.details.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.ScreenLoadingTag
import br.com.messore.tech.exchanges.details.viewmodel.DetailUiState
import br.com.messore.tech.exchanges.testing.ExchangeDataFactory.createExchangeById
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayLoadingWhenDataIsBeingFetched() {
        val state = DetailUiState(
            loading = true,
        )

        composeTestRule.setContent {
            DetailScreen(
                state = state,
            )
        }

        composeTestRule.onNodeWithTag(ScreenLoadingTag).assertIsDisplayed()
    }

    @Test
    fun detailScreenDisplaysExchangeDetails() {
        val state = DetailUiState(
            loading = false,
            exchange = createExchangeById("BINANCE"),
        )

        composeTestRule.setContent {
            DetailScreen(state = state)
        }

        composeTestRule.onNodeWithText("Binance").assertIsDisplayed()
        composeTestRule.onNodeWithText("https://www.binance.com/").assertIsDisplayed()

        composeTestRule.onNodeWithText("Volume 1 day USD:").assertIsDisplayed()
        composeTestRule.onNodeWithText("$29,497,214,160.58").assertIsDisplayed()

        composeTestRule.onNodeWithText("Volume 1 hour USD:").assertIsDisplayed()
        composeTestRule.onNodeWithText("$128,855,794.37").assertIsDisplayed()

        composeTestRule.onNodeWithText("Volume 1 month USD:").assertIsDisplayed()
        composeTestRule.onNodeWithText("$1,234,297,742,912.71").assertIsDisplayed()
    }

    @Test
    fun detailScreenClickWebsiteOpensBrowser() {
        var clickedUrl: String? = null
        val state = DetailUiState(
            loading = false,
            exchange = Exchange(
                exchangeId = "BINANCE",
                website = "https://www.binance.com/",
                name = "Binance",
                volume1DayUsd = 29497214160.58,
                volume1HourUsd = 123456789.0,
                volume1MonthUsd = 123456789.0,
                image = "https://s3.eu-central-1.amazonaws.com/bbxt-static-icons/type-id/png_64/12706e78b23e41e2afd42eecc28d9bbc.png",
            ),
        )

        composeTestRule.setContent {
            DetailScreen(
                state = state,
                onWebsiteClick = { url -> clickedUrl = url },
            )
        }

        composeTestRule.onNodeWithText("https://www.binance.com/").performClick()
        assertEquals("https://www.binance.com/", clickedUrl)
    }
}
