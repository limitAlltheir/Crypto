package by.limitalltheir.crypto.data.cryptocurrency.mapper

import by.limitalltheir.crypto.data.cryptocurrency.dto.Data
import by.limitalltheir.crypto.data.cryptocurrency.entity.Rates

class RatesMapper {

    fun map(from: Data) = Rates(
        id = from.id ?: 0,
        name = from.name.orEmpty(),
        price = from.quote?.uSD?.price ?: 0.0,
        change = from.quote?.uSD?.percentChange1h ?: 0.0
    )
}