package br.com.messore.tech.exchanges.features.exchanges.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.presentation.designsystem.ExchangesTheme
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.AppImage
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.ScreenLoading
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.Search
import br.com.messore.tech.exchanges.features.exchanges.viewmodel.ExchangeUiAction
import br.com.messore.tech.exchanges.features.exchanges.viewmodel.ExchangeUiState

@Composable
fun ExchangeScreen(
    state: ExchangeUiState,
    onAction: (ExchangeUiAction) -> Unit,
) {
    ScreenLoading(
        loading = state.loading,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Search(
            value = state.searchTerm,
            onSearchChanged = {
                onAction(ExchangeUiAction.OnSearchTermChange(it))
            },
            placeholder = "Search for an exchange",
            onClearTextClicked = {
                onAction(ExchangeUiAction.OnSearchTermChange(""))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(
                key = { it.exchangeId },
                items = state.filteredExchanges.ifEmpty { state.exchanges },
            ) { exchange ->
                ExchangeItem(
                    exchange = exchange,
                    onExchangeSelect = {
                        onAction(ExchangeUiAction.SelectExchange(it))
                    },
                )
            }
        }
    }
}

@Composable
fun ExchangeItem(
    exchange: Exchange,
    onExchangeSelect: (Exchange) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .clickable(onClick = { onExchangeSelect(exchange) }),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppImage(
                imageUrl = exchange.image,
                contentDescription = exchange.name,
                modifier = Modifier.padding(horizontal = 8.dp),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.55f),
            ) {
                Text(
                    maxLines = 2,
                    text = exchange.name,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Medium,
                    )
                )

                Text(
                    text = exchange.exchangeId,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp)
                        .align(Alignment.End),
                    maxLines = 1,
                    text = exchange.formattedVolume,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.End,
                    )
                )
            }
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ExchangeScreenPreview() {
    ExchangesTheme {
        ExchangeScreen(
            state = ExchangeUiState(
                exchanges = listOf(
                    Exchange(
                        name = "Binance",
                        website = "https://www.binance.com/",
                        exchangeId = "BINANCE",
                        volume1DayUsd = 29497214160.58,
                    ),
                    Exchange(
                        name = "Kraken",
                        website = "https://www.kraken.com/",
                        exchangeId = "KRAKEN",
                        volume1DayUsd = 1966886113.43,
                    ),
                ),
            ),
            onAction = {}
        )
    }
}