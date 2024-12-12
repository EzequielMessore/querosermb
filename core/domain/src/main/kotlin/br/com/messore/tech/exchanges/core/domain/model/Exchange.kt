package br.com.messore.tech.exchanges.core.domain.model

import java.text.NumberFormat
import java.util.Locale

data class Exchange(
    val name: String,
    val website: String,
    val exchangeId: String,
    val volume1DayUsd: Double,
    val image: String? = null,
) {
    val formattedVolume: String
        get() {
            val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
            return numberFormat.format(volume1DayUsd)
        }
}
