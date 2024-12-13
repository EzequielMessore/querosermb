package br.com.messore.tech.exchanges.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val url: String,
    val exchangeId: String,
)
