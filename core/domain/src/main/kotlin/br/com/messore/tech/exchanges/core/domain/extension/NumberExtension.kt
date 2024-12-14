package br.com.messore.tech.exchanges.core.domain.extension

import java.text.NumberFormat
import java.util.Locale

fun Double.formatCurrency(): String {
    return NumberFormat.getCurrencyInstance(Locale.US).format(this)
}
