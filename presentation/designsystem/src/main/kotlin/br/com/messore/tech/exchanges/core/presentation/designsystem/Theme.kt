package br.com.messore.tech.exchanges.core.presentation.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = CryptoYellow,
    secondary = CryptoDarkGray,
    tertiary = CryptoMediumGray,
    background = CryptoBackgroundDark,
    surface = CryptoBackgroundDark,
    onPrimary = CryptoBackgroundDark,
    onSecondary = CryptoLightGray,
    onTertiary = CryptoLightGray,
    onBackground = CryptoLightGray,
    onSurface = CryptoLightGray
)

private val LightColorScheme = lightColorScheme(
    primary = CryptoYellow,
    secondary = CryptoLightGray,
    tertiary = CryptoMediumGray,
    background = CryptoBackgroundLight,
    surface = CryptoBackgroundLight,
    onPrimary = CryptoDarkGray,
    onSecondary = CryptoDarkGray,
    onTertiary = CryptoDarkGray,
    onBackground = CryptoDarkGray,
    onSurface = CryptoDarkGray
)

@Composable
fun ExchangesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
