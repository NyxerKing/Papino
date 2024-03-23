package ru.papino.restaurant.domain.repository.models

internal sealed class OrdersResponse {

    data class Success(val orders: List<OrderModel>) : OrdersResponse()

    data class Error(val error: Exception) : OrdersResponse()
}