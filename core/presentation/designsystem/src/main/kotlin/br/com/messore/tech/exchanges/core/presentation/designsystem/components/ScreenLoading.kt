package br.com.messore.tech.exchanges.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import br.com.messore.tech.exchanges.core.presentation.designsystem.ExchangesTheme

@Composable
fun ScreenLoading(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    progressColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    if (loading) {
        Loading(modifier = Modifier.fillMaxSize(), progressColor = progressColor)
    } else {
        Column(
            modifier = modifier,
            content = content,
        )
    }
}

@Preview
@Composable
private fun ScreenLoadingPreview() {
    ExchangesTheme {
        ScreenLoading(loading = true)
    }
}
