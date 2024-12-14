package br.com.messore.tech.exchanges.view.model

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class DispatchersProvider(
    val io: CoroutineDispatcher,
    val main: CoroutineDispatcher,
    val default: CoroutineDispatcher,
    val unconfined: CoroutineDispatcher,
) {
    constructor() : this(
        Dispatchers.Main,
        Dispatchers.IO,
        Dispatchers.Default,
        Dispatchers.Unconfined,
    )
}
