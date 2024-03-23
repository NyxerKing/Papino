package ru.papino.restaurant.domain.repository

import ru.papino.restaurant.domain.repository.models.OrdersResponse

internal interface OrdersRepository {
    suspend fun getOrders(userId: Long): OrdersResponse
}