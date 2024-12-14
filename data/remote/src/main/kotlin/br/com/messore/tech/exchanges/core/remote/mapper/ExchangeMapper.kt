package br.com.messore.tech.exchanges.core.remote.mapper

import br.com.messore.tech.exchanges.core.remote.model.Exchange as ExchangeRemote
import br.com.messore.tech.exchanges.core.domain.model.Exchange as ExchangeDomain
import br.com.messore.tech.exchanges.core.remote.model.Image as ImageRemote
import br.com.messore.tech.exchanges.core.domain.model.Image as ImageDomain

fun ExchangeRemote.toDomain(): ExchangeDomain {
    return ExchangeDomain(
        name = name ?: "",
        website = website ?: "",
        exchangeId = exchangeId ?: "",
        volume1DayUsd = volume1DayUsd ?: 0.0,
        volume1HourUsd = volume1HourUsd ?: 0.0,
        volume1MonthUsd = volume1MonthUsd ?: 0.0,
    )
}

fun ImageRemote.toDomain(): ImageDomain {
    return ImageDomain(
        url = url ?: "",
        exchangeId = exchangeId ?: "",
    )
}
