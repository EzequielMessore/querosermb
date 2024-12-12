package br.com.messore.tech.exchange.core.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exchange(
    val name: String? = null,
    val website: String? = null,
    @SerialName("exchange_id")
    val exchangeId: String? = null,
    @SerialName("volume_1day_usd")
    val volume1DayUsd: Double? = null,
)
