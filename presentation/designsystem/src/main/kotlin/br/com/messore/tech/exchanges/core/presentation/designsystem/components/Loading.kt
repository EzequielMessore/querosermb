package br.com.messore.tech.exchanges.core.presentation.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Loading(
    modifier: Modifier = Modifier,
    progressColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = progressColor,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}
