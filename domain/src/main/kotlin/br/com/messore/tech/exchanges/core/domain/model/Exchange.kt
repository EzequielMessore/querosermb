package br.com.messore.tech.exchanges.core.domain.model

data class Exchange(
    val name: String,
    val website: String,
    val exchangeId: String,
    val volume1DayUsd: Double,
    val volume1HourUsd: Double,
    val volume1MonthUsd: Double,
    val image: String? = null,
)
