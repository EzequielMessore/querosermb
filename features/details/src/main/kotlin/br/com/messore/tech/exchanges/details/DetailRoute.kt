package br.com.messore.tech.exchanges.details

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.Button
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.ErrorScreen
import br.com.messore.tech.exchanges.details.ui.DetailScreen
import br.com.messore.tech.exchanges.details.viewmodel.DetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailRoute(
    onBackPressed: () -> Unit,
    viewModel: DetailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    if (state.hasError) {
        ErrorScreen(
            onDismiss = onBackPressed,
            primaryButton = Button(
                text = "Fechar",
                onClick = onBackPressed,
            )
        )
        return
    }

    val context = LocalContext.current
    DetailScreen(
        state = state,
        onBackPressed = onBackPressed,
        onWebsiteClick = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
        }
    )
}