package br.com.messore.tech.exchanges.core.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val url: String? = null,
    @SerialName("exchange_id")
    val exchangeId: String? = null,
)
