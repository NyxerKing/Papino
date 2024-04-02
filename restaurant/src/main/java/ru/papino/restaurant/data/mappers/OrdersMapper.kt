package ru.papino.restaurant.data.mappers

import ru.papino.restaurant.data.datasource.net.models.OrderJsonModel
import ru.papino.restaurant.domain.repository.models.OrderModel

internal class OrdersMapper {

    fun toDomain(data: OrderJsonModel) = OrderModel(
        id = data.id,
        created = data.created,
        products = data.products.map { product -> product.name },
        sum = data.sum,
        status = data.status
    )
}