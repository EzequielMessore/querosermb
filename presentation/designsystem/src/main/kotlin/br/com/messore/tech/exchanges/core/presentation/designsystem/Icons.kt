package br.com.messore.tech.exchanges.core.presentation.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.vectorResource
import br.com.messore.tech.exchanges.presentation.designsystem.R

@Composable
fun ImageVector.asPainter(): Painter {
    return rememberVectorPainter(this)
}

val CurrencyExchange: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.currency_exchange)

val NavigationIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.arrow_left)

val LinkIcon: ImageVector
    @Composable
    get() = ImageVector.vectorResource(id = R.drawable.link)
