package ru.papino.restaurant.domain.response

import ru.papino.restaurant.core.net.repeater.RequestRepeatError
import ru.papino.restaurant.domain.models.OrderModel

internal sealed class OrdersResponse {

    data class Success(val orders: List<OrderModel>) : OrdersResponse()

    data class Error(val error: Exception) : OrdersResponse(), RequestRepeatError
}