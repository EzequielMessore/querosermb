package br.com.messore.tech.exchages

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import br.com.messore.tech.exchanges.core.presentation.designsystem.ExchangesTheme
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.Button
import br.com.messore.tech.exchanges.core.presentation.designsystem.components.ErrorScreen
import br.com.messore.tech.exchanges.details.DetailRoute
import br.com.messore.tech.exchanges.features.exchanges.ExchangeRoute
import br.com.messore.tech.exchanges.navigation.Routes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExchangesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavHost()
                    }
                }
            }
        }
    }
}

@Composable
private fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.Exchanges) {
        composable<Routes.Exchanges> {
            ExchangeRoute(onExchangeSelect = {
                navController.navigate(Routes.ExchangeDetail(it.exchangeId))
            })
        }
        composable<Routes.ExchangeDetail> {
            DetailRoute(
                onBackPressed = navController::popBackStack
            )
        }
    }
}
