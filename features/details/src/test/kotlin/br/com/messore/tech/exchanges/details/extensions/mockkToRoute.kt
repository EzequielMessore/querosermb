package br.com.messore.tech.exchanges.details.extensions

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import io.mockk.every
import io.mockk.mockkStatic

internal inline fun <reified T : Any> SavedStateHandle.mockkToRoute(page: T) {
    mockkStatic("androidx.navigation.SavedStateHandleKt")
    every { toRoute<T>() } returns page
}
