package br.com.messore.tech.exchanges.details.ui

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.messore.tech.exchanges.core.domain.extension.formatCurrency
import br.com.messore.tech.exchanges.core.domain.model.Exchange
import br.com.messore.tech.exchanges.core.presentation.designsystem.ExchangesTheme
import br.com.messore.tech.exchanges.core.presentation.designsystem.LinkIcon
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.AppImage
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.ScreenLoading
import br.com.messore.tech.exchanges.details.viewmodel.DetailUiState

@Composable
fun DetailScreen(
    state: DetailUiState,
    onBackPressed: () -> Unit = {},
    onWebsiteClick: (String) -> Unit = {},
) = Column {
    AppBar(state, onBackPressed)

    ScreenLoading(
        loading = state.loading,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        AppImage(
            imageUrl = state.exchange?.image.orEmpty(),
            contentDescription = "Exchange",
            modifier = Modifier
                .size(120.dp)
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                DetailItem(
                    label = "Volume 1 day USD:",
                    value = state.exchange?.volume1DayUsd?.formatCurrency().orEmpty(),
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                )
                DetailItem(
                    label = "Volume 1 hour USD:",
                    value = state.exchange?.volume1HourUsd?.formatCurrency().orEmpty(),
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                DetailItem(
                    label = "Volume 1 month USD:",
                    value = state.exchange?.volume1MonthUsd?.formatCurrency().orEmpty(),
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                WebsiteItem(
                    onWebsiteClick = onWebsiteClick,
                    website = state.exchange?.website.orEmpty(),
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppBar(state: DetailUiState, onBackPressed: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = state.exchange?.name.orEmpty(),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
    )
}

@Composable
private fun DetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
            ),
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
            ),
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
private fun WebsiteItem(
    website: String,
    onWebsiteClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onWebsiteClick(website)
            }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = website,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
            ),
            color = MaterialTheme.colorScheme.primary,
        )

        Icon(
            imageVector = LinkIcon,
            contentDescription = "Open Website",
            tint = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
private fun DetailScreenPreview() {
    ExchangesTheme {
        DetailScreen(
            state = DetailUiState(
                loading = false,
                exchange = Exchange(
                    name = "Binance",
                    website = "https://www.binance.com/",
                    exchangeId = "BINANCE",
                    volume1HourUsd = 1361461136.3,
                    volume1DayUsd = 14175247309.7,
                    volume1MonthUsd = 1178683342003.13,
                ),
            ),
        )
    }
}
