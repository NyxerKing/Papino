package ru.papino.restaurant.presentation.orders.mappers

import ru.papino.restaurant.domain.repository.models.OrderModel
import ru.papino.restaurant.domain.repository.models.status.OrderStatus
import ru.papino.restaurant.presentation.orders.models.OrderUIModel

internal class OrdersMapper {

    fun toUI(data: OrderModel) = OrderUIModel(
        id = data.id,
        created = data.created,
        products = data.products,
        sum = data.sum,
        status = data.status
    )

    fun toStatusKit(statusUIModel: OrderStatus) = when (statusUIModel) {
        OrderStatus.CREATED -> ru.papino.uikit.constants.OrderStatus.CREATED
        OrderStatus.DELIVERED -> ru.papino.uikit.constants.OrderStatus.DELIVERED
        OrderStatus.COMPLETED -> ru.papino.uikit.constants.OrderStatus.COMPLETED
    }
}