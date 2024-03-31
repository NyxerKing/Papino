package ru.papino.restaurant.presentation.basket.mappers

import ru.papino.restaurant.domain.repository.models.OrderProductRequestModel
import ru.papino.restaurant.presentation.basket.models.BasketUIModel

internal class BasketMapper {

    fun toDomain(data: BasketUIModel) = OrderProductRequestModel(
        productId = data.id.toLong(),
        count = data.count
    )
}