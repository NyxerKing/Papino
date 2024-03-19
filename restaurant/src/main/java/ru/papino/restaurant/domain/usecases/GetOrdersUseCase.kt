package ru.papino.restaurant.domain.usecases

import ru.papino.restaurant.domain.repository.OrdersRepository
import ru.papino.restaurant.domain.repository.models.OrdersResponse

internal class GetOrdersUseCase(
    private val repository: OrdersRepository
) {

    suspend operator fun invoke(userId: Long): OrdersResponse {
        return repository.getOrders(userId)
    }
}